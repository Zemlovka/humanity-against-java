package com.zemlovka.haj.utils.dto.server;

import com.zemlovka.haj.utils.dto.Resource;


public record LogoutResponseDTO(boolean isSuccesful) implements Resource {
}
