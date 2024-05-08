package com.zemlovka.haj.server.game;

import com.zemlovka.haj.server.CardsSupplier;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class Lobby {
    public static final int DEFAULT_PLAYER_CARDS_NUMBER = 5;
    private final int capacity;
    private final String name;
    private final String password;
    private final ConcurrentHashMap<UUID, User> users;
    private final Flags flags;
    private final List<CardDTO> questionCardsPool;
    private final List<CardDTO> answerCardsPool;
    private final Map<Integer, CardDTO> roundsToQuestionCardsMap;
    private Integer currentRound = 0;


    public Lobby(int capacity, String name, String password, ConcurrentHashMap<UUID, User> users) {
        this.capacity = capacity;
        this.name = name;
        this.password = password;
        this.users = users;
        this.flags = new Flags();
        questionCardsPool = CardsSupplier.getQuestionCardPool();
        answerCardsPool = CardsSupplier.getAnswerCardPool();
        roundsToQuestionCardsMap = new HashMap<>();
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

    public ConcurrentHashMap<UUID, User> getUsers() {
        return users;
    }

    public Flags getFlags() {
        return flags;
    }

    public synchronized List<CardDTO> selectRandomAnswerCards(int amount) {
        Random random = new Random();
        List<CardDTO> returnCards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            returnCards.add(answerCardsPool.remove(random.nextInt(answerCardsPool.size())));
        }
        return returnCards;
    }

    public synchronized CardDTO selectRandomQuestionCard() {
        Random random = new Random();
        if (!roundsToQuestionCardsMap.containsKey(currentRound))
            roundsToQuestionCardsMap.put(currentRound,
                    questionCardsPool.remove(random.nextInt(questionCardsPool.size())));
        return roundsToQuestionCardsMap.get(currentRound);
    }

    public synchronized void nextRound() {
        currentRound++;
    }
}
