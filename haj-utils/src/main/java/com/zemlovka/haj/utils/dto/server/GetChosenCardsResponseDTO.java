package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;

import java.util.List;


public record GetChosenCardsResponseDTO(List<CardDTO> cards) implements Resource {
}
