package ru.yandex.practicum.filmorate.storage;


import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;


public interface FilmStorage {
    public Film create(Film film) throws WrongInputException;

    public Collection<Film> getAllFilms();

    public Film updateFilm( Film film) throws WrongUpdateException;

    public void deleteLike(int filmId, int userId) throws NotFoundException;
}
