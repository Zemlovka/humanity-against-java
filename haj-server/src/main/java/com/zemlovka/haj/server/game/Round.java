package com.zemlovka.haj.server.game;

import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class Round {
    private final int roundNumber;
    private final Lobby lobby;
    private final CardDTO questionCard;
    private final Map<User, CardDTO> userToChosenCardsMap;
    private final Map<User, CardDTO> userToVotedCardsMap;
    private Map.Entry<User,CardDTO> winner = null;

    public Round(CardDTO questionCard, Lobby lobby, int roundNumber) {
        this.lobby = lobby;
        this.questionCard = questionCard;
        this.roundNumber = roundNumber;
        userToChosenCardsMap = new HashMap<>();
        userToVotedCardsMap = new HashMap<>();
    }

    public synchronized Map.Entry<User, CardDTO> getWinner() {
        if (winner != null)
            return winner;
        if (lobby.getUsers().size() != userToVotedCardsMap.size())
            throw new IllegalStateException("Not every player has voted");
        Map<CardDTO, Integer> cardToVotes = new HashMap<>();
        userToVotedCardsMap.values().forEach(card -> {
            if (cardToVotes.containsKey(card))
                cardToVotes.put(card, cardToVotes.get(card) + 1);
            else
                cardToVotes.put(card, 0);
        });
        int winnerVotes = 0;
        CardDTO winner = null;
        for (Map.Entry<CardDTO, Integer> entry: cardToVotes.entrySet()) {
            if (entry.getValue() >= winnerVotes) {
                winnerVotes = entry.getValue();
                winner = entry.getKey();
            }
        }
        CardDTO finalWinner = winner;
        Map.Entry<User, CardDTO> winnerEntry = userToChosenCardsMap.entrySet().stream().filter(entry -> entry.getValue().equals(finalWinner)).findFirst().orElseThrow();
        lobby.addPoints(winnerEntry.getKey());
        return winnerEntry;
    }

    public synchronized void addChosenCard(User user, CardDTO chosenCard) {
        userToChosenCardsMap.put(user, chosenCard);
    }
    public synchronized void voteCard(User user, int votedCardId) {
        if (!userToChosenCardsMap.values().stream().map(CardDTO::getId).collect(Collectors.toSet()).contains(votedCardId))
            throw new IllegalStateException("USer voted for the card that is not in the chosen cards");
        userToVotedCardsMap.put(user, userToChosenCardsMap.values().stream().filter(card -> card.getId() == votedCardId).findFirst().orElseThrow());
    }

    public Map<User, CardDTO> getChosenCards() {
        return userToChosenCardsMap;
    }
    public Map<User, CardDTO> getVotedCards() {
        return userToVotedCardsMap;
    }

    public CardDTO getQuestionCard() {
        return questionCard;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
