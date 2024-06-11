package com.zemlovka.haj.server;

import com.zemlovka.haj.server.game.Lobby;
import com.zemlovka.haj.server.game.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final Set<Session> sessions;

    private final Acceptor acceptor;
    private final ConcurrentHashMap<String, Lobby> lobbies;
    private final ConcurrentHashMap<String, User> users;

    public Server(int port) {
        sessions = new HashSet<>();
        lobbies = new ConcurrentHashMap<>();
        acceptor = new Acceptor(port, this);
        users = new ConcurrentHashMap<>();
    }

    public void terminate() {
        acceptor.terminate();

        synchronized(sessions) {
            for (Session s : sessions) {
                s.terminate();
            }
        }
    }

    public void newSession(Socket clientSocket) {
        log.info("Creating a new session");
        Session session = new Session(clientSocket, this, lobbies, users);

        synchronized(sessions) {
            sessions.add(session);
        }
    }
}
