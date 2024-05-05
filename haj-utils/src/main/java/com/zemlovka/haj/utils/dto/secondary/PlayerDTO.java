package com.zemlovka.haj.utils.dto.secondary;

public class PlayerDTO {
    private String name;
    private boolean isClient;
    public PlayerDTO(String name) {
        this.name = name;
    }
    public PlayerDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
