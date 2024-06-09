package com.zemlovka.haj.server.game;

import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.util.HashMap;
import java.util.Map;


public class Round {
    private final CardDTO questionCard;
    private final Map<User, CardDTO> userToChosenCardsMap;

    public Round(CardDTO questionCard) {
        this.questionCard = questionCard;
        userToChosenCardsMap = new HashMap<>();
    }

    public synchronized void addChosenCard(User user, CardDTO chosenCard) {
        userToChosenCardsMap.put(user, chosenCard);
    }

    public Map<User, CardDTO> getChosenCards() {
        return userToChosenCardsMap;
    }

    public CardDTO getQuestionCard() {
        return questionCard;
    }
}
