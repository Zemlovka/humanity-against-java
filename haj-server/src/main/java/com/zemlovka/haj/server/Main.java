package com.zemlovka.haj.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final int DEFAULT_PORT = 8082;
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Server is starting");
        Scanner scanner = new Scanner(System.in);
        Integer port = null;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            log.info("Port not found in the program arguments, waiting for user input: ");
            try {
                port = Integer.parseInt(scanner.nextLine());
            } catch (Exception e2) {
                log.info("Port not found in the user input, using default port: {}", DEFAULT_PORT );
                port = DEFAULT_PORT;
            }
        }
        Server server = new Server(port);

        log.info("Waiting for the ENTER key to end the server");

        scanner.nextLine();

        log.info("ENTER detected, shutting down server");
        server.terminate();
        Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);
    }}