package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class FilmController {
    @Autowired
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PutMapping("/films/{filmId}/like/{userId}")
    public void filmLiking(@PathVariable("filmId") int filmId,
                           @PathVariable("userId") int userId) {

        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteFilmLike
            (@PathVariable("id") int filmId,
             @PathVariable("userId") int userId) throws  NotFoundException {

        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/films/popular")
    @ResponseBody
    public List<Film> TheMostPopular(@RequestParam(required = false, defaultValue = "10") int count) {
        return filmService.theMostPopularFilms(count);
    }


    @GetMapping("/films/{id}")
    public Film getFilm(@PathVariable int id) throws NotFoundException {
        return filmService.getFilm(id);
    }

    @GetMapping("/films")
    public Collection<Film> getAllUsers() {
        return filmService.filmStorage.getAllUsers();
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws WrongInputException {
        return filmService.filmStorage.create(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@RequestBody Film film) throws WrongUpdateException {
        return filmService.filmStorage.updateFilm(film);
    }


}
