package com.zemlovka.haj.utils.dto.secondary;

import java.util.UUID;


public class PlayerDTO {
    private String name;
    private UUID uuid;
    public PlayerDTO(String name, UUID uuid) {
        this.name = name;
        uuid = uuid;
    }
    public PlayerDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
