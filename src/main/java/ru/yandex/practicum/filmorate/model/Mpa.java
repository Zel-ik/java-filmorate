package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mpa {
    Integer id;
    String name;

    public Mpa(String name) {
        this.name = name;
    }

    public Mpa(Integer id) {
        this.id = id;
    }

    public Mpa(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
