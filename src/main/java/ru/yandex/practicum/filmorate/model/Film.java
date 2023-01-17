package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    int id;
    @NotNull @NotBlank String name;
    @NotNull @NotBlank String description;
    @NotNull LocalDate releaseDate;
    @NotNull int duration;
}
