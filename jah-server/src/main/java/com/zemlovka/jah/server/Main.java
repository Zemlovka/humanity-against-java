package com.zemlovka.jah.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Server is starting");

        Server server = new Server();

        log.info("Waiting for the ENTER key to end the server");

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        log.info("ENTER detected, shutting down server");
        server.terminate();
    }}