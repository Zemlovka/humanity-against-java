package com.zemlovka.haj.utils.dto.secondary;

public class CardDTO {
    private int id;
    private String text;
    public CardDTO(int id, String text) {
        this.id = id;
        this.text = text;
    }
    public CardDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardDTO cardDTO = (CardDTO) o;

        return getId() == cardDTO.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
