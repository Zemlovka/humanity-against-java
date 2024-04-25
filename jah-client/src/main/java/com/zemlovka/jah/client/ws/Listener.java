package com.zemlovka.jah.client.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Listener implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private static final int MAX_CONNECTION_DOWN_S = 30;
    private static final int MAX_RECONNECT_ATTEMPTS = 30;
    private static final int RECONNECT_INTERVAL_S = 1;
    private final InputStream InputStream;
    private final Main mainProcess;

    public Listener(InputStream InputStream, Main mainProcess) {
        this.InputStream = InputStream;
        this.mainProcess = mainProcess;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(InputStream))) {
            int reconnectAttempt = 0;
            Long connectionDownTime = null;
            while (true) {
                String readLine = br.readLine();
                if (readLine != null) {
                    System.out.println(readLine);
                } else {
                    if (connectionDownTime == null)
                        connectionDownTime = System.currentTimeMillis();
                    if (reconnectAttempt == MAX_RECONNECT_ATTEMPTS ||
                            System.currentTimeMillis() - connectionDownTime > MAX_CONNECTION_DOWN_S * 1000) {
                        log.info("Connection lost, disconnecting...");
                        break;
                    }
                    log.info("Recconnect attempt {}", ++reconnectAttempt);
                    Thread.sleep(RECONNECT_INTERVAL_S * 1000);
                }
            }
        } catch (IOException e) {
            log.error("Exception occurred while listening for incoming communication.", e);
        } catch (InterruptedException e) {
            log.error("Exception occurred while putting thread to sleep", e);
        } finally {
            mainProcess.end();
        }
    }
}
