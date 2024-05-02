package com.zemlovka.haj.utils.dto.client;

import com.zemlovka.haj.utils.dto.Resource;


public record CreateLobbyDTO(String name, String password, int size) implements Resource {
}
