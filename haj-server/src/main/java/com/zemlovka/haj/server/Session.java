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
    private ServerWsActions wsActions;

    public Session(Socket clientSocket, Server server, ConcurrentHashMap<String, Lobby> lobbies) {
        log.debug("Vytvarim objekt Session a pripravuji samostatna vlakna pro jeho obsluhu.");
        this.clientSocket = clientSocket;
        this.server = server;
        userData = new User();
        wsActions = new ServerWsActions(workLock, writer, lobbies, userData);

        keepAlive = true;
        closed = false;


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
    private void startReceivingMessages() throws IOException {
        while (keepAlive) {
            String message = reader.readLine();
            wsActions.resolveAndSendCommand(message);
        }

    }
}
