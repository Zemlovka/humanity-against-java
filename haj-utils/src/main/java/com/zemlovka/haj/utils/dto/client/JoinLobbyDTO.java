package com.zemlovka.haj.utils.dto.client;

import com.zemlovka.haj.utils.dto.Resource;


public record JoinLobbyDTO(String lobbyName, String password) implements Resource {
}
