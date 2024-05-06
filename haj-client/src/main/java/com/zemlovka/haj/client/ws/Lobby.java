package com.zemlovka.haj.client.ws;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final String name;
    private String password = null;
    private final int size;
    private List<Player> players;

    private List<AnswerCard> answerCards;
    private List<AnswerCard> playerCards;
    private QuestionCard questionCard;

    public Lobby(String name, String password, int size) {
        this.name = name;
        this.password = password;
        this.size = size;
        this.players = new ArrayList<>();
        this.answerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayerToList(Player player) {
        players.add(player);
    }

    public void removePlayerFromList(Player player) {
        players.remove(player);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getSize() {
        return size;
    }

    public void setQuestionCard(QuestionCard questionCard) {
        this.questionCard = questionCard;
    }

    public QuestionCard getQuestionCard() {
        return questionCard;
    }
}
