package com.zemlovka.haj.client.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private static final Logger log = LoggerFactory.getLogger(Client.class);
    private String message;

    public Client() {
    }

    @Override
    public void run() {
        log.info("Client started.");
        try (
            Socket clientSocket = new Socket("127.0.0.1", 8082);
            PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true))
        {
            Thread listenerThread = new Thread(new Listener(clientSocket.getInputStream()));
            listenerThread.start();

            boolean keepAlive = true;
            while (keepAlive) {
                if (message != null) {
                    pw.println(message);
                    message = null;
                }

                if ("QUIT".equalsIgnoreCase(message)) {
                    keepAlive = false;
                }
                Thread.sleep(50);
            }
        } catch (IOException e) {
            log.error("Doslo k vyjimce behem komunikace se serverem.", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setMessage(String message) {
        this.message = message;
    }
}