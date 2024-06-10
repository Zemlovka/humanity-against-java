package com.zemlovka.haj.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.server.command.*;
import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.ResourceObjectMapperFactory;
import com.zemlovka.haj.utils.dto.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSOutput;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.zemlovka.haj.utils.GlobalUtils.compileUUID;


public class ServerWsActions {
    private static final Logger log = LoggerFactory.getLogger(ServerWsActions.class);
    private final Object workLock;
    private final PrintWriter writer;
    private final ConcurrentHashMap<String, Lobby> lobbies;
    private final User userData;
    private final Set<ServerCommand<? extends Resource>> commandSet;

    private final ObjectMapper objectMapper;

    public ServerWsActions(Object workLock, PrintWriter writer, ConcurrentHashMap<String, Lobby> lobbies, User userData) {
        this.workLock = workLock;
        this.writer = writer;
        this.lobbies = lobbies;
        this.userData = userData;
        this.commandSet = new HashSet<>();
        this.objectMapper = ResourceObjectMapperFactory.getObjectMapper();
        populateStaticCommandSet();
    }

    private void populateStaticCommandSet() {
        commandSet.add(new LeaveLobbyCommand(this, userData));
        commandSet.add(new LoginCommand(this, userData));
        commandSet.add(new LogoutCommand(this, userData));
        commandSet.add(new JoinLobbyCommand(this, lobbies, userData));
        commandSet.add(new CreateLobbyCommand(this, lobbies, userData));
        commandSet.add(new FetchLobbysCommand(this, lobbies, userData));
        commandSet.add(new StartGameCommand(this, userData));
        commandSet.add(new FetchPlayersCommand(this, userData));
        commandSet.add(new ChooseCardCommand(this, userData));
        commandSet.add(new GetChosenCardsCommand(this, userData));
        commandSet.add(new VoteCardCommand(this, userData));
    }

    public void resolveAndSendCommand(String message) throws JsonProcessingException {
        CommunicationObject clientCommunicationObject = objectMapper.readValue(message, CommunicationObject.class);
        ConnectionHeader clientHeader = clientCommunicationObject.header();
        log.info("Command {} received from client {} with object type and communicationId {}",
                clientHeader.commandName(), compileUUID(clientHeader.clientID()), compileUUID(clientHeader.communicationUuid()));
        for (ServerCommand command : commandSet) {
            if (command.resolve(clientCommunicationObject)) {
                log.info("Command with communicationId {} resolved to server command {}",
                        compileUUID(clientHeader.communicationUuid()), command.getName());
                command.execute(clientCommunicationObject.body(), clientHeader);
                return;
            }
        }
        log.error("Command {} with communicationId {} wasn't resolved", clientHeader.commandName(),
                compileUUID(clientHeader.communicationUuid()));
    }


    public void sendMessage(Resource body, ConnectionHeader clientHeader) {
        log.info("Sending response of type {} to command {} with communication id {}, to client {}", body.getClass().getSimpleName(), clientHeader.commandName(), compileUUID(clientHeader.communicationUuid()), compileUUID(clientHeader.clientID()));
        ConnectionHeader responseHeader = new ConnectionHeader(
                clientHeader.clientID(),
                clientHeader.communicationUuid(),
                body.getClass().getSimpleName(),
                clientHeader.commandName());
        CommunicationObject responseObject = new CommunicationObject(responseHeader, body);
        synchronized (workLock) {
            try {
                writer.println(objectMapper.writeValueAsString(responseObject));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
