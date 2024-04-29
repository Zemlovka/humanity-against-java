package com.zemlovka.haj.client.ws;

import com.zemlovka.haj.client.fx.App;

import java.io.InputStream;


public class WSActions {

    private Client client;
    public WSActions() {
        Client client = new Client();
        client.start(); //will it work?
        this.client = client;
    }

    public void connect(String username) {
        client.setMessage("USER " + username);
    }
}
