package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap();

    private int filmMapId = 0;
    private static int filmPersonalId = 1;

    @GetMapping("/films")
    public Collection<Film> getAllUsers() {
        return films.values();
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws WrongInputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate pastDate = LocalDate.parse("1895-12-28", formatter);
        if (film.getName().isBlank()) {
            throw new WrongInputException("Поле name пустое");
        } else if (film.getDuration() <= 0) {
            throw new WrongInputException("Поле duration меньше нуля");
        } else if (film.getDescription().length() > 200) {
            throw new WrongInputException("Поле description содержит больше 200 символов");
        } else if (film.getReleaseDate().isBefore(pastDate)) {
            throw new WrongInputException("Поле releaseDate некорректно");
        } else {
            film.setId(filmPersonalId);
            filmPersonalId += 1;
            films.put(filmPersonalId, film);
            filmMapId += 1;
            return film;
        }
    }

    public Film updateUser(@RequestBody Film film) throws WrongUpdateException {
        //данный метод перебирает ключи HashMap films, сравнивая их values с объектом film, если не находит совпадения -
        // выбрасывает error, в ином случае заменяет найденный value на объект film.

        if (film != null) {

            for (Integer f : films.keySet()) {
                if (!(films.get(f).getId() == film.getId())) {
                    throw new WrongUpdateException("");
                } else {
                    films.put(f, film);
                }
            }
        }
        return film;
    }
}
