package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.secondary.LobbyDTO;


public record JoinLobbyResponseDTO(boolean successful, LobbyDTO lobby) implements Resource {
}
