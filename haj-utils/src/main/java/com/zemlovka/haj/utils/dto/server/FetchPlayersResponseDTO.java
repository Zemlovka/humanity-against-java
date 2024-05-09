package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.secondary.PlayerDTO;

import java.util.Set;


public record FetchPlayersResponseDTO(Set<PlayerDTO> players, boolean awaitNewPlayers) implements Resource {
}
