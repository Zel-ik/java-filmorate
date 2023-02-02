package storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;


@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final HashMap<Integer, Film> films = new HashMap<>();

    private static int filmPersonalId = 1;

    public HashMap<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Film create(Film film) throws WrongInputException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate pastDate = LocalDate.parse("1895-12-28", formatter);
        if (film.getDuration() <= 0) {
            throw new WrongInputException("Поле duration меньше нуля");
        } else if (film.getDescription().length() > 200) {
            throw new WrongInputException("Поле description содержит больше 200 символов");
        } else if (film.getReleaseDate().isBefore(pastDate)) {
            throw new WrongInputException("Поле releaseDate некорректно");
        } else {
            film.setId(filmPersonalId);
            filmPersonalId += 1;
            films.put(filmPersonalId, film);
            return film;
        }
    }

    @Override
    public Collection<Film> getAllUsers() {
        return films.values();
    }

    @Override
    public Film updateUser(Film film) throws WrongUpdateException {
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
