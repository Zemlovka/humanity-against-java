package com.zemlovka.haj.client.ws.entities;

/**
 * Abstract class representing a card in the game.
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (getId() != card.getId()) return false;
        return getText().equals(card.getText());
    }

    @Override
    public int hashCode() {
        int result = getText().hashCode();
        result = 31 * result + getId();
        return result;
    }
}
