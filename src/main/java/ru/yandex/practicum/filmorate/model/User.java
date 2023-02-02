package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class User {
    Set<User> friends;
    int id;
    @Email String email;
    @NotNull String login;
    String name;
    LocalDate birthday;

    public void setFriends(User friend) {
        friends.add(friend);
    }
}
