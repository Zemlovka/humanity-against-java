package com.zemlovka.jah.client.ws;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        new Main().startClient();
    }

    private void startClient() {
        log.info("Client started.");

        try (
                Scanner scanner = new Scanner(System.in);
                Socket clientSocket = new Socket("127.0.0.1", 8081);
                PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true))
        {
            Thread listenerThread = new Thread(new Listener(clientSocket.getInputStream(), this));
            listenerThread.start();

            boolean keepAlive = true;
            while (keepAlive && clientSocket.isConnected() && listenerThread.isAlive()) {
                String message = scanner.nextLine();

                if ("QUIT".equalsIgnoreCase(message)) {
                    keepAlive = false;
                }

                pw.println(message);
            }
        } catch (IOException e) {
            log.error("Doslo k vyjimce behem komunikace se serverem.", e);
        }
    }

    void end() {
        log.info("Shutting down client...");
        System.exit(1);
    }
}