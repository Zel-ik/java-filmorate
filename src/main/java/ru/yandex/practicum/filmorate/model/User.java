package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    int id;
    @Email String email;
    @NotNull String login;
    String name;
    LocalDate birthday;

}
