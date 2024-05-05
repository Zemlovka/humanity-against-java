package com.zemlovka.haj.server;

import com.zemlovka.haj.server.user.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    private final Set<Session> sessions;

    private final Deque<String> messages;

    private Acceptor acceptor;

    private Sender sender;
    private ConcurrentHashMap<String, Lobby> lobbies;

    public Server() {
        sessions = new HashSet<>();
        messages = new LinkedList<>();
        lobbies = new ConcurrentHashMap<>();

        acceptor = new Acceptor(8082, this);
        sender = new Sender(this);
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
        log.info("Vytvarim novou session.");
        Session session = new Session(clientSocket, this, lobbies);

        synchronized(sessions) {
            sessions.add(session);
        }
    }

    public Set<Session> getAllSessions() {
        synchronized (sessions) {
            return sessions;
        }
    }

    public void addMessage(String message) {
        synchronized (messages) {
            messages.add(message);

            log.debug("Pridali jsme zpravu do fronty k odeslani, vlakno se probouzi.");
            messages.notifyAll();
        }
    }

    public String pollMessage() throws InterruptedException {
        synchronized (messages) {
            while (messages.isEmpty()) {
                log.debug("Fronta zprav k odeslani je prazdna, vlakno ceka.");
                messages.wait();
            }

            return messages.poll();
        }
    }

}
