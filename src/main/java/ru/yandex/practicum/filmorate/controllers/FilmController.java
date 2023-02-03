package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController{
    private final FilmService filmService;
    private final InMemoryFilmStorage filmStorage;


    @PutMapping("PUT /films/{id}/like/{userId}")
    public Film filmLiking(@PathVariable("id") int filmId,
                           @PathVariable("userId") int userId){

        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("DELETE /films/{id}/like/{userId}")
    public void deleteFilmLike(@PathVariable("id") int filmId,
                               @PathVariable("userId") int userId){

        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/films")
    public Collection<Film> getAllUsers() {
        return filmStorage.getAllUsers();
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws WrongInputException {
        return filmStorage.create(film);
    }

    public Film updateUser(@RequestBody Film film) throws WrongUpdateException {
        return filmStorage.updateUser(film);
    }
}
