package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;


//todo
public record UserConnectionResponseDTO(boolean isSuccesful, String message) implements Resource {
}
