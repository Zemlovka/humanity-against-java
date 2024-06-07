package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.secondary.LobbyDTO;


public record JoinLobbyResponseDTO(JoinState joinState, LobbyDTO lobby) implements Resource {
    public enum JoinState{
        SUCCESS,
        LOBBY_FULL,
        WRONG_PASSWORD,
        LOBBY_DOES_NOT_EXIST;
    }
}
