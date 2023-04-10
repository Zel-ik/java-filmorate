package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class Film {

    @JsonIgnoreProperties
    Set<User> likes = new HashSet<>();
    ArrayList<Genre> genres = new ArrayList<>();
    int rate;
    Map<String, Object> mpa = new HashMap<>();
    int id;
    @NotNull @NotBlank String name;
    @NotNull @NotBlank String description;
    @NotNull LocalDate releaseDate;
    @NotNull int duration;


    public void deleteLike(int userId){
        for(User u : likes){
            if(u.getId() == userId){
                likes.remove(u);
                break;
            }
        }
    }
}
