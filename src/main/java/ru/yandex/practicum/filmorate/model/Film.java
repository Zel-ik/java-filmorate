package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class Film {

    Set<User> likes;
    int id;
    @NotNull @NotBlank String name;
    @NotNull @NotBlank String description;
    @NotNull LocalDate releaseDate;
    @NotNull int duration;

    public void setLikes(User like) {
        likes.add(like);
    }

    public void deleteLike(int userId){
        for(User u : likes){
            if(u.getId() == userId){
                likes.remove(u);
                break;
            }
        }
    }
}
