package com.zemlovka.haj.server.game;

import com.google.common.collect.ImmutableMap;
import com.zemlovka.haj.server.CardsSupplier;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Lobby {
    private static final int DEFAULT_PLAYER_CARDS_NUMBER = 5;
    private final int capacity;
    private final String name;
    private final String password;
    private final ConcurrentHashMap<UUID, User> users;
    private final Flags flags;
    private final List<CardDTO> questionCardsPool;
    private final List<CardDTO> answerCardsPool;
    private final List<Round> rounds;
    private final Map<UUID, Map<Integer, CardDTO>> userToCardsMap;
    private Round currentRound = null;


    public Lobby(int capacity, String name, String password, ConcurrentHashMap<UUID, User> users) {
        this.capacity = capacity;
        this.name = name;
        this.password = password;
        this.users = users;
        this.flags = new Flags();
        questionCardsPool = CardsSupplier.getQuestionCardPool();
        answerCardsPool = CardsSupplier.getAnswerCardPool();
        rounds = new ArrayList<>();
        userToCardsMap = new HashMap<>();
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Map<UUID, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }
    public synchronized boolean removeUser(User user) {
        userToCardsMap.remove(user.getUuid());
        return users.remove(user.getUuid()) != null;
    }
    public synchronized void addUser(User user) {
        users.put(user.getUuid(), user);
        userToCardsMap.put(user.getUuid(), new HashMap<>());
    }

    public Flags getFlags() {
        return flags;
    }

    public synchronized Map<Integer, CardDTO> getAnswerCards(User user) {
        return userToCardsMap.get(user.getUuid());
    }

    public synchronized void refreshAnswerCards(User user) {
        Map<Integer, CardDTO> userCards = userToCardsMap.get(user.getUuid());
        if (userCards.size() <= DEFAULT_PLAYER_CARDS_NUMBER) {
            Random random = new Random();
            for (int i = 0; i < DEFAULT_PLAYER_CARDS_NUMBER - userCards.size(); i++) {
                CardDTO card = answerCardsPool.remove(random.nextInt(answerCardsPool.size()));
                userCards.put(card.getId(), card);
            }
        }
    }

    public synchronized void nextRound() {
        Random random = new Random();
        currentRound = new Round(questionCardsPool.remove(random.nextInt(questionCardsPool.size())));
        rounds.add(currentRound);
    }

    public synchronized Round getCurrentRound() {
        return currentRound;
    }
}
