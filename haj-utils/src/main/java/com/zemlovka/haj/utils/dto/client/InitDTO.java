package com.zemlovka.haj.utils.dto.client;

import com.zemlovka.haj.utils.dto.Resource;

import java.util.UUID;


public record InitDTO(String username, UUID clientUuid) implements Resource {
}
