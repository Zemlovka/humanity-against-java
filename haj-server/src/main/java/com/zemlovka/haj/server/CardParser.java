package com.zemlovka.haj.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.io.IOException;
import java.util.List;


public class CardParser {
    private final static String QUESTION_CARDS_FILENAME = "questionCards.json";

    private final ObjectMapper objectMapper;
    public CardParser() {
        this.objectMapper = new ObjectMapper();
    }
    public List<CardDTO> parseQuestionCards() {
        try {
            return objectMapper.readValue(CardParser.class.getClassLoader().getResource(QUESTION_CARDS_FILENAME), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
