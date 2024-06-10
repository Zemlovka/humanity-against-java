package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;
import com.zemlovka.haj.utils.dto.secondary.LobbyDTO;

import java.util.Set;


public record LobbyListDTO(Set<LobbyDTO> lobbies) implements Resource {

    @Override
    public boolean isPolling() {
        return true;
    }
}
