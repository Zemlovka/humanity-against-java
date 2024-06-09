package com.zemlovka.haj.server.game;


public class Flags {
    private final Flag lobbyReadyFlag;
    private final Flag newPlayerFlag;
    private final Flag chooseCardsFlag;
    private final Flag voteCardsFlag;

    public Flags() {
        lobbyReadyFlag = new SimpleFlag();
        newPlayerFlag = new ResubmittingFlag();
        chooseCardsFlag = new ResubmittingFlag();
        voteCardsFlag = new SimpleFlag();
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

    public Flag voteCards() {
        return voteCardsFlag;
    }
}
