package com.zemlovka.haj.server;

import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
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
    private ServerWsActions wsActions;

    public Session(Socket clientSocket, Server server, ConcurrentHashMap<String, Lobby> lobbies, Map<String, User> users) {
        log.debug("Creating a new session");
        this.clientSocket = clientSocket;
        this.server = server;
        userData = new User();

        keepAlive = true;
        closed = false;


        log.debug("Creating reader and writer objects");
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            log.error("An exception occurred while creating reader and writer objects", e);
            close();
        }

        log.debug("Creating new thead for the session");
        Thread thread = new Thread(() -> {
            log.debug("A thread for the new session has been started");

            try {
                startReceivingMessages();
            } catch (IOException e) {
                if (keepAlive) {
                    log.error("An exception happened during the message receiving, terminating session", e);
                }
            } finally {
                close();
            }
        });
        wsActions = new ServerWsActions(workLock, writer, lobbies, userData, users);

        // thread.setDaemon(true);
        thread.start();
    }

    private void close() {
        synchronized (workLock) {
            if (closed) {
                return;
            }

            log.debug("Closing client-socket and terminating all serving threads");

            try {
                clientSocket.close();
                userData.shutdownLobbyPollingExecutor();
            } catch (IOException e) {
                log.debug("An exception happened while closing server-socket", e);
            }

            closed = true;
        }
    }

    public void terminate() {
        log.info("The connection was ended from the server side");
        keepAlive = false;
        close();
    }
    private void startReceivingMessages() throws IOException {
        while (keepAlive) {
            String message = reader.readLine();
            wsActions.resolveAndSendCommand(message);
        }

    }
}
