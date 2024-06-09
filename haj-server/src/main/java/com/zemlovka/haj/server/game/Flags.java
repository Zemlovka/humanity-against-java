package com.zemlovka.haj.server.game;


public class Flags {
    private final Flag lobbyReadyFlag;
    private final Flag newPlayerFlag;
    private final Flag chooseCardsFlag;

    public Flags() {
        lobbyReadyFlag = new Flag();
        newPlayerFlag = new ResubmittingFlag();
        chooseCardsFlag = new ResubmittingFlag();
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
