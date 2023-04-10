package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Genre {
    int id;
    String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
