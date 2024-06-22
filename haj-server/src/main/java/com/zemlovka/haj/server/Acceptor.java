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

    private final int incomingPort;

    private final Server server;

    private ServerSocket serverSocket;


    public Acceptor(int incomingPort, Server server) {
        log.debug("Creating an Acceptor object and preparing its thread");
        keepAlive = true;
        closed = false;

        this.incomingPort = incomingPort;
        this.server = server;

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        log.info("Starting Acceptor thread");

        try {
            serverSocket = new ServerSocket(incomingPort);

            while (keepAlive) {
                log.info("Waiting for the client connection");
                Socket clientSocket = serverSocket.accept();

                log.info("Client connected, opening communication");
                server.newSession(clientSocket);
            }
        } catch (IOException e) {
            if (keepAlive) {
                log.error("An exception happened while waiting for the next client, terminating the server", e);
                server.terminate();
            }
        } finally {
            close();
        }

        log.info("Acceptor thread has been ended");
    }

    private void close() {
        synchronized (closeLock) {
            if (closed) {
                return;
            }

            if (serverSocket == null) {
                log.debug("Server-socket does not exist, terminating the Acceptor thread");
            } else {
                log.debug("Closing server-socket and terminating the Acceptor thread");

                try {
                    serverSocket.close();
                } catch (IOException e) {
                    log.debug("An exception happened while closing server socket, terminating the server", e);
                }
            }

            closed = true;
        }
    }

    public void terminate() {
        log.info("Acceptor thread has been terminated");
        keepAlive = false;

        close();
    }
}
