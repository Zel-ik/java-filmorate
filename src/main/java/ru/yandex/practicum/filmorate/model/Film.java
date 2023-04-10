package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Film {
    @NonFinal
    int id;

    @NotEmpty
    String name;

    @Size(max = 200)
    String description;

    LocalDate releaseDate;

    @Positive
    Integer duration;
    @NonFinal
    Integer rate; //количество лайков
    @NonFinal
    Mpa mpa;
    @NonFinal
    Collection<Genre> genres;

    public Film(String name, String description, LocalDate releaseDate, int duration, Integer rate, Mpa mpa,
                List<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        if (rate == null) {
            this.rate = 0;
        } else {
            this.rate = rate;
        }
        this.mpa = mpa;
        if (genres == null) {
            this.genres = new ArrayList<>();
        } else {
            this.genres = genres;
        }
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("NAME", name);
        values.put("DESCRIPTION", description);
        values.put("RELEASE_DATE", releaseDate);
        values.put("DURATION", duration);
        values.put("RATE", rate);
        values.put("MPA_ID", mpa.getId());
        return values;
    }
}
