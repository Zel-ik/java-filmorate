package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    @Autowired
    public final InMemoryFilmStorage filmStorage;
    @Autowired
    public final InMemoryUserStorage userStorage;

    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, int userId){
        // Добавляем объект User в множество likes объекта Film
        filmStorage.getFilms().get(filmId).getLikes().add(userStorage.getUsers().get(userId));
    }

    public void deleteLike(int filmId, int userId) throws  NotFoundException {
        if(userId < 0){
            throw  new NotFoundException("400, Фильм не существует или Пользователь не существует");
        }
        if(filmStorage.getFilms().get(filmId) == null) return;
        filmStorage.getFilms().get(filmId).getLikes().remove(userStorage.getUsers().get(userId));
    }

    public List<Film> theMostPopularFilms(int count){
        List<Film> films = new ArrayList<>(filmStorage.getFilms().values());
        // через компаратор сравниваем количество лайков объектов Film через likes.size()
        films.sort(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return Integer.compare(o1.getLikes().size(), o2.getLikes().size());
            }
        });

        return films.stream().limit(count).collect(Collectors.toList());
    }


    public Film getFilm(int id) throws NotFoundException {
        HashMap<Integer, Film> films = new HashMap<>(filmStorage.getFilms());
        for(Film f : films.values()){
         if(f.getId() == id){
             return f;
         }
        }
        throw new NotFoundException("фильм не найден");
    }
}
