package com.zemlovka.haj.server.game;

import com.zemlovka.haj.server.CardsSupplier;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class Lobby {
    private final int capacity;
    private final String name;
    private final String password;
    private final ConcurrentHashMap<UUID, User> users;
    private final Flags flags;
    private final List<CardDTO> questionCardsPool;
    private final List<CardDTO> answerCardsPool;


    public Lobby(int capacity, String name, String password, ConcurrentHashMap<UUID, User> users) {
        this.capacity = capacity;
        this.name = name;
        this.password = password;
        this.users = users;
        this.flags = new Flags();
        questionCardsPool = CardsSupplier.getQuestionCardPool();
        answerCardsPool = CardsSupplier.getAnswerCardPool();
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

//    public List<CardDTO> selectRandom
}
