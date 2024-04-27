package com.zemlovka.haj.client.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Listener implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Listener.class);
    private InputStream inputStream;

    public Listener(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream))) {
            while (true) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            log.error("Exception occurred while listening for incoming communication.", e);
        }
    }
}
