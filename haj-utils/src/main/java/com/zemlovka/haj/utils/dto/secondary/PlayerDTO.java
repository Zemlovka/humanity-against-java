package com.zemlovka.haj.utils.dto.secondary;

import java.util.UUID;


public class PlayerDTO {
    private String name;
    private UUID uuid;
    private int points;
    public PlayerDTO(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
        points = 0;
    }
    public PlayerDTO(String name, UUID uuid, int points) {
        this.name = name;
        this.uuid = uuid;
        this.points = points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "name='" + name + '\'' +
                ", uuid=" + uuid +
                ", points=" + points +
                '}';
    }
}
