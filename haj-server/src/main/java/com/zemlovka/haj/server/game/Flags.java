package com.zemlovka.haj.server.game;


public class Flags {
    private final Flag lobbyReadyFlag;
    private final Flag newPlayerFlag;
    private final Flag chooseCardsFlag;

    public Flags() {
        lobbyReadyFlag = new Flag();
        newPlayerFlag = new Flag();
        chooseCardsFlag = new Flag();
    }

    public Flag lobbyReady() {
        return lobbyReadyFlag;
    }

    public Flag newPlayer() {
        return newPlayerFlag;
    }

    public Flag chooseCards() {
        return chooseCardsFlag;
    }
}
