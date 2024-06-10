package com.zemlovka.haj.client.ws;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final String name;
    private String password = null;
    private final int capacity;
    private List<Player> players;

    private List<AnswerCard> answerCards;
    private List<AnswerCard> playerCards;
    private QuestionCard questionCard;

    public Lobby(String name, String password, int capacity) {
        this.name = name;
        this.password = password;
        this.capacity = capacity;
        this.players = new ArrayList<>();
        this.answerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }
    public Lobby(String name, List<Player> players, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.players = players;
        this.answerCards = new ArrayList<>();
        this.playerCards = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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

    public int getCapacity() {
        return capacity;
    }

    public List<AnswerCard> getAnswerCards() {
        return answerCards;
    }

    public void addAnswerCard(AnswerCard answerCard) {
        answerCards.add(answerCard);
    }

    public List<AnswerCard> getPlayerCards() {
        return playerCards;
    }

    public void addPlayerCard(AnswerCard playerCard) {
        playerCards.add(playerCard);
    }

    public void setAnswerCards(List<AnswerCard> answerCards) {
        this.answerCards = answerCards;
    }

    public void setPlayerCards(List<AnswerCard> playerCards) {
        this.playerCards = playerCards;
    }

    public void setQuestionCard(QuestionCard questionCard) {
        this.questionCard = questionCard;
    }

    public QuestionCard getQuestionCard() {
        return questionCard;
    }
}
