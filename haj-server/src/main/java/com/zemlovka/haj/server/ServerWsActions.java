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

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


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
        commandSet.add(new LoginCommand(this, userData));
        commandSet.add(new LogoutCommand(this, userData));
        commandSet.add(new JoinLobbyCommand(this, lobbies, userData));
        commandSet.add(new CreateLobbyCommand(this, lobbies, userData));
        commandSet.add(new FetchLobbysCommand(this, lobbies));
        commandSet.add(new StartGameCommand(this, userData));
    }

    public void resolveAndSendCommand(String message) throws JsonProcessingException {
        CommunicationObject clientCommunicationObject = objectMapper.readValue(message, CommunicationObject.class);
        ConnectionHeader clientHeader = clientCommunicationObject.header();
        log.debug("Command {} received from client {} with object type and communicationId {}",
                clientHeader.commandName(), clientHeader.clientID(), clientHeader.communicationUuid());
        int i = 0;
        for (ServerCommand command : commandSet) {
            if (command.resolve(clientCommunicationObject)) {
                command.execute(clientCommunicationObject.body(), clientHeader);
                break;
            }
        }
    }


    public void sendMessage(Resource body, ConnectionHeader clientHeader) {
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
