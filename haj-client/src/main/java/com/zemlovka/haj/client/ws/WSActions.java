package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.fx.App;

import java.io.InputStream;


public class WSActions {

    private Client client;
    public WSActions() {
        Runner runner = new Runner();
        runner.start();
        Client client = new Client();
        client.start(); //will it work?
        this.client = client;
    }

    static class Runner extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.println("asdasdasd");
        }
    }

    public void connect(String username) {
        client.setMessage("USER " + username);
    }
}
