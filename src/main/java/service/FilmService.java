package service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    InMemoryFilmStorage filmStorage = new InMemoryFilmStorage();
    UserService userService;


    public FilmService(UserService userService) {
        this.userService = userService;
    }

    public Film addLike(int userId, int filmId){
        // Добавляем объект User в множество likes объекта Film
        filmStorage.getFilms().get(filmId).setLikes(userService.userStorage.getUsers().get(userId));

        return filmStorage.getFilms().get(filmId);
    }

    public void deleteLike(int userId, int filmId){
        filmStorage.getFilms().get(filmId).deleteLike(userId);
    }

    public List<Film> theMostPopularFilms(){
        List<Film> films = new ArrayList<>(filmStorage.getFilms().values());
        films.sort(new Comparator<Film>() {
            @Override
            public int compare(Film o1, Film o2) {
                return Integer.compare(o1.getLikes().size(), o2.getLikes().size());
            }
        });

        log.info(films + "топ 10 фильмов");

        return films.stream().limit(10).collect(Collectors.toList());
    }


}
