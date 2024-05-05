package com.zemlovka.haj.client.ws;

public abstract class Card {
    private final String text;
    private final int id;

    protected Card(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public int getId() {
        return id;
    }
}
