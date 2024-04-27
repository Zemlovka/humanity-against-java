package com.zemlovka.haj.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Acceptor implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Acceptor.class);

    private final Object closeLock = new Object();

    private volatile boolean keepAlive;
    private volatile boolean closed;

    private int incomingPort;

    private Server server;

    private ServerSocket serverSocket;


    public Acceptor(int incomingPort, Server server) {
        log.debug("Vytvarim objekt Acceptor a pripravuji samostatne vlakno pro jeho beh.");
        keepAlive = true;
        closed = false;

        this.incomingPort = incomingPort;
        this.server = server;

        Thread thread = new Thread(this);
        // thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        log.info("Startuji Acceptor vlakno.");

        try {
            serverSocket = new ServerSocket(incomingPort);

            while (keepAlive) {
                log.info("Cekam na pripojeni dalsiho klienta.");
                Socket clientSocket = serverSocket.accept();

                log.info("Klient pripojen, zahajuji komunikaci.");
                server.newSession(clientSocket);
            }
        } catch (IOException e) {
            if (keepAlive) {
                log.error("Doslo k vyjimce pri cekani na pripojeni dalsiho klienta, ukoncuji cely server.", e);
                server.terminate();
            }
        } finally {
            close();
        }

        log.info("Acceptor vlakno bylo ukonceno.");
    }

    private void close() {
        synchronized (closeLock) {
            if (closed) {
                return;
            }

            if (serverSocket == null) {
                log.debug("Server-socket neexistuje, ukoncuji Acceptor vlakno.");
            } else {
                log.debug("Uzaviram server-socket a ukoncuji Acceptor vlakno.");

                try {
                    serverSocket.close();
                } catch (IOException e) {
                    log.debug("Doslo k vyjimce pri uzavirani server-socketu behem ukoncovani Acceptor vlakna, neni treba resit.", e);
                }
            }

            closed = true;
        }
    }

    public void terminate() {
        log.info("Prijem novych pripojeni byl ukoncen.");
        keepAlive = false;

        close();
    }
}
