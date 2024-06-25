package com.zemlovka.haj.server.game;

import com.zemlovka.haj.server.CardsSupplier;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * A lobby class for all the functions connected to a lobby logic
 */
public class Lobby {
    private static final Logger log = LoggerFactory.getLogger(Lobby.class);
    private static final int DEFAULT_PLAYER_CARDS_NUMBER = 5;
    private static final int POINTS_FOR_WIN = 5;
    private final int capacity;
    private final String name;
    private final ConcurrentHashMap<UUID, User> users;
    private final Flags flags;
    private final List<CardDTO> questionCardsPool;
    private final List<CardDTO> answerCardsPool;
    private final List<Round> rounds;
    private final Map<UUID, Map<Integer, CardDTO>> userToCardsMap;
    private final Map<UUID, Integer> userToPoints;
    private boolean isGameActive;
    private int currentRoundNumber = -1;
    private int roundNumber;


    public Lobby(int capacity, String name, int roundNumber) {
        this.capacity = capacity;
        this.name = name;
        this.users = new ConcurrentHashMap<>();
        this.flags = new Flags();
        this.roundNumber = roundNumber;
        questionCardsPool = CardsSupplier.getQuestionCardPool();
        answerCardsPool = CardsSupplier.getAnswerCardPool();
        rounds = new ArrayList<>();
        userToCardsMap = new HashMap<>();
        userToPoints = new HashMap<>();
        isGameActive = true;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public Map<UUID, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }
    public synchronized void removeUser(User user) {
        userToCardsMap.remove(user.getUuid());
        users.remove(user.getUuid());
    }
    public synchronized void addUser(User user) {
        users.put(user.getUuid(), user);
        userToPoints.put(user.getUuid(), 0);
        userToCardsMap.put(user.getUuid(), new HashMap<>());
    }

    public Flags getFlags() {
        return flags;
    }

    public synchronized Map<Integer, CardDTO> getAnswerCards(User user) {
        return userToCardsMap.get(user.getUuid());
    }

    public synchronized CardDTO removeAnswerCards(User user, int cardId) {
        return userToCardsMap.get(user.getUuid()).remove(cardId);
    }

    public synchronized void refreshAnswerCards(User user) {
        Map<Integer, CardDTO> userCards = userToCardsMap.get(user.getUuid());
        if (userCards.size() <= DEFAULT_PLAYER_CARDS_NUMBER) {
            Random random = new Random();
            int currentNumberOfCards = userCards.size();
            for (int i = 0; i < DEFAULT_PLAYER_CARDS_NUMBER - currentNumberOfCards; i++) {
                CardDTO card = answerCardsPool.remove(random.nextInt(answerCardsPool.size()));
                userCards.put(card.getId(), card);
            }
        }
    }

    /**
     *
     */
    public synchronized void nextRound() {
        currentRoundNumber++;
        Random random = new Random();
        Round round = new Round(questionCardsPool.remove(random.nextInt(questionCardsPool.size())), this, currentRoundNumber+1);
        rounds.add(round);
    }

    public int getPoints(User user) {
        return userToPoints.get(user.getUuid());
    }

    public void addPoints(User user) {
        userToPoints.put(user.getUuid(), userToPoints.get(user.getUuid()) + POINTS_FOR_WIN);
    }

    public boolean isRoundPlayable() {
        return rounds.size() <= roundNumber;
    }

    public synchronized Round getCurrentRound() {
        return rounds.get(currentRoundNumber);
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        isGameActive = gameActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lobby lobby = (Lobby) o;

        return getName().equals(lobby.getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
