package storage;


import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;


public interface FilmStorage {
    public Film create(Film film) throws WrongInputException;

    public Collection<Film> getAllUsers();

    public Film updateUser( Film film) throws WrongUpdateException;
}
