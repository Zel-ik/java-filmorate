package ru.yandex.practicum.filmorate.storage;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

@Component
public class FilmDbStorage implements FilmStorage {


    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        jdbcTemplate.update("INSERT INTO film_list(name, description, release_date, duration, rate, MPA_ID) " +
                        "VALUES ( ?, ?, ?, ?, ?, ?)", film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getRate(), film.getMpa());

        return film;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return jdbcTemplate.query("SELECT * FROM film_list", new BeanPropertyRowMapper<>(Film.class));
    }

    @Override
    public Film updateFilm(Film film) {
        jdbcTemplate.update("UPDATE film_list SET name=?, description=?, release_date=?, duration=?, mpa_id = ?, " +
                        "rate = ? WHERE film_id = ?", film.getId(), film.getName(), film.getDescription(),
                film.getReleaseDate(), film.getDuration(), film.getMpa(), film.getRate(), film.getId());
        return film;
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        jdbcTemplate.update("DELETE FROM liked_film WHERE film_id =? AND user_id = ?", filmId, userId);
    }
}
