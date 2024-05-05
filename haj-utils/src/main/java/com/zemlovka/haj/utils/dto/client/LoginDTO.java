package com.zemlovka.haj.utils.dto.client;

import com.zemlovka.haj.utils.dto.Resource;

import java.util.UUID;


public record LoginDTO(String username, UUID clientUuid) implements Resource {
}
