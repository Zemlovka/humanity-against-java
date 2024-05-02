package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;

import java.util.UUID;


public record LobbyCreatedDTO(String name, UUID uuid, int size) implements Resource {
}
