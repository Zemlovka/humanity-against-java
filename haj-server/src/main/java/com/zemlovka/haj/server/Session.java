package com.zemlovka.haj.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.server.command.*;
import com.zemlovka.haj.server.user.Lobby;
import com.zemlovka.haj.server.user.User;
import com.zemlovka.haj.utils.CommunicationObject;
import com.zemlovka.haj.utils.ConnectionHeader;
import com.zemlovka.haj.utils.ResourceObjectMapperFactory;
import com.zemlovka.haj.utils.dto.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Session {
    private static final Logger log = LoggerFactory.getLogger(Session.class);
    private final Object workLock = new Object();

    private volatile boolean keepAlive;
    private volatile boolean closed;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket clientSocket;
    private Server server;
    private User userData;
    private final Object lock = new Object();
    private ObjectMapper objectMapper;
    private Set<ResolvableCommand<? extends Resource, ? extends Resource>> staticCommandSet;
    private ConcurrentHashMap<String, Lobby> lobbies;

    public Session(Socket clientSocket, Server server, ConcurrentHashMap<String, Lobby> lobbies) {
        log.debug("Vytvarim objekt Session a pripravuji samostatna vlakna pro jeho obsluhu.");
        this.clientSocket = clientSocket;
        this.server = server;
        this.objectMapper = ResourceObjectMapperFactory.getObjectMapper();
        this.lobbies = lobbies;

        keepAlive = true;
        closed = false;

        userData = new User();
        populateStaticCommandSet();

        log.debug("Vytvarim reader a writer objekty.");
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("Doslo k vyjimce pri vytvareni session.", e);
            close();
        }

        log.debug("Vytvarim nove vlakno pro obsluhu klienta.");
        Thread thread = new Thread(() -> {
            log.debug("Vlakno pro novou session bylo spusteno.");

            try {
                startReceivingMessages();
            } catch (IOException e) {
                if (keepAlive) {
                    log.error("Doslo k vyjimce pri prijmu zpravy, ukoncuji session.", e);
                }
            } finally {
                close();
            }

            log.debug("Vlakno session uzivatele '{}' bylo ukonceno.", userData.getUsername());
        });

        // thread.setDaemon(true);
        thread.start();
    }

    private void close() {
        synchronized (workLock) {
            if (closed) {
                return;
            }

            log.debug("Uzaviram client-socket a ukoncuji obsluzna vlakna.");

            try {
                clientSocket.close();
            } catch (IOException e) {
                log.debug("Doslo k vyjimce pri uzavirani client-socketu behem ukoncovani spojeni, neni treba resit.", e);
            }

            closed = true;
        }
    }

    public void terminate() {
        log.info("Spojeni bylo ukonceno ze strany serveru.");
        keepAlive = false;

//        sendMessage("ERR Server se ukoncuje, spojeni bude uzavreno.");
        close();
    }

    private void populateStaticCommandSet() {
        staticCommandSet = new HashSet<>();
        staticCommandSet.add(new InitCommand(userData));
        staticCommandSet.add(new JoinLobbyCommand(lobbies, userData));
        staticCommandSet.add(new CreateLobbyCommand(lobbies, userData));
        staticCommandSet.add(new FetchLobbysCommand(lobbies));
    }

    private void startReceivingMessages() throws IOException {
        while (keepAlive) {
            String message = reader.readLine();
            CommunicationObject clientCommunicationObject = objectMapper.readValue(message, CommunicationObject.class);
            ConnectionHeader clientHeader = clientCommunicationObject.header();
            log.debug("Message received from client " + clientHeader.clientID()
                    + " with object type " + clientHeader.objectType()
                    + " and communicationId " + clientHeader.communicationUuid());
            ResolvableCommand<Resource,Resource> staticCommand = null;
            Resource responseResource = null;
            for (ResolvableCommand resolvableCommand : staticCommandSet) {
                if (resolvableCommand.resolve(clientCommunicationObject))
                    staticCommand = resolvableCommand;
            }
            if (staticCommand != null) {
                responseResource = staticCommand.run(clientCommunicationObject.body());
            }


            ConnectionHeader responseHeader = new ConnectionHeader(
                    clientHeader.clientID(),
                    clientHeader.communicationUuid(),
                    responseResource.getClass().getSimpleName(),
                    clientHeader.commandName());
            CommunicationObject responseObject = new CommunicationObject(responseHeader, responseResource);
            sendMessage(responseObject);
        }

    }

    public void sendMessage(CommunicationObject message) {
        synchronized (workLock) {
            try {
                writer.println(objectMapper.writeValueAsString(message));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
