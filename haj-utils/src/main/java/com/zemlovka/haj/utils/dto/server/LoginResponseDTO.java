package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;


//todo
public record LoginResponseDTO(boolean isSuccesful, String message) implements Resource {
}
