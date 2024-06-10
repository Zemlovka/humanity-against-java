package com.zemlovka.haj.client.ws;

public interface ConnectionStatusListener {
    void onConnectionLost();
    void onConnectionEstablished();
}