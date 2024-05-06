package com.zemlovka.haj.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.io.IOException;
import java.util.List;


public class CardsSupplier {
    private final static String QUESTION_CARDS_FILENAME = "questionCards.json";
    private final static String ANSWER_CARDS_FILENAME = "answerCards.json";

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static List<CardDTO> getQuestionCardPool() {
        try {
            return OBJECT_MAPPER.readValue(CardsSupplier.class.getClassLoader().getResource(QUESTION_CARDS_FILENAME), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<CardDTO> getAnswerCardPool() {
        try {
            return OBJECT_MAPPER.readValue(CardsSupplier.class.getClassLoader().getResource(ANSWER_CARDS_FILENAME), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
