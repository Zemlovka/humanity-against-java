package com.zemlovka.haj.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sender implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Sender.class);

    private Server server;

    private boolean keepAlive;

    public Sender(Server server) {
        this.server = server;

        keepAlive = true;

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        log.info("Startuji Sender vlakno.");

        try {
            while (keepAlive) {
                String message = server.pollMessage();

                for (Session s : server.getAllSessions()) {
                    log.info("Odesilam zpravu: '{}'", message);
                    s.sendMessage(message);
                }
            }
        } catch (InterruptedException e) {
            log.info("Sender vlakno bylo preruseno.");
            keepAlive = false;
        }

        log.info("Sender vlakno bylo ukonceno.");
    }
}
