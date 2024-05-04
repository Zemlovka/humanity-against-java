package com.zemlovka.haj.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Session {
    private static final Logger log = LoggerFactory.getLogger(Session.class);

    private final Object workLock = new Object();

    private volatile boolean keepAlive;
    private volatile boolean closed;

    private BufferedReader reader;

    private PrintWriter writer;

    private Socket clientSocket;

    private Server server;

    private String userName;

    private PrintWriter pw;

    private final Object lock = new Object();

    public Session(Socket clientSocket, Server server) {
        log.debug("Vytvarim objekt Session a pripravuji samostatna vlakna pro jeho obsluhu.");
        this.clientSocket = clientSocket;
        this.server = server;

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

            log.debug("Vlakno session uzivatele '{}' bylo ukonceno.", userName);
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

        sendMessage("ERR Server se ukoncuje, spojeni bude uzavreno.");
        close();
    }

    private void startReceivingMessages() throws IOException {
        while (keepAlive) {
            String message = reader.readLine();
            String[] parts = message.split(" ", 2);

            String command = parts[0];
            String text = parts.length > 1 ? parts[1] : null;

            if ("QUIT".equalsIgnoreCase(command) || "Q".equalsIgnoreCase(command)) {
                log.info("Spojeni bylo ukonceno ze strany klienta.");
                keepAlive = false;
                sendMessage("OK");
                continue;
            }

            if ("USER".equalsIgnoreCase(command) || "U".equalsIgnoreCase(command)) {
                log.info("Nastavuji prezdivku uzivatele na '{}'.", text);
                userName = text;
                sendMessage("OK");
                continue;
            }

            if ("MESSAGE".equalsIgnoreCase(command) || "M".equalsIgnoreCase(command)) {
                if (userName == null || userName.isBlank()) {
                    log.warn("Uzivatel nema nastavenou prezdivku, jeho zpravy prozatim nelze prijimat.");
                    sendMessage("ERR Nejprve si nastavte prezdivku prikazem USER.");
                    continue;
                }

                String outMsg = userName + ": " + text;

                server.addMessage(outMsg);
                sendMessage("OK");

                /* for (Session s : server.getAllSessions()) {
                    log.debug("Odesilam zpravu uzivateli '{}': {}", s.userName, outMsg);
                    s.sendMessage(outMsg);
                } */

                continue;
            }


            log.warn("Serveru byl zaslan neznamy prikaz.");
            sendMessage("ERR Tento prikaz neznam.");
        }

    }

    public void sendMessage(String message) {
        synchronized (workLock) {
            writer.println(message);
        }
    }
}
