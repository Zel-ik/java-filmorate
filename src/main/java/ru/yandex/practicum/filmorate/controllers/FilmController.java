package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;
import service.FilmService;
import storage.InMemoryFilmStorage;

import java.util.Collection;

@Slf4j
@RestController
public class FilmController{
    @Autowired
    private final FilmService filmService;
    InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


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
