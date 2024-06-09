package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.secondary.CardDTO;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;


public record VoteCardResponseDTO(CardDTO winnerCard, PlayerDTO winnerPlayer) implements Resource {
}
