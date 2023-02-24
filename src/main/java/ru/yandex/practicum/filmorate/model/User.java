package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


@Data
public class User {
    @JsonIgnoreProperties("friends")
    Set<User> friends = new HashSet<>();
    HashMap<User, Boolean> friendsStatus = new HashMap<>();
    int id;
    @Email String email;
    @NotNull String login;
    String name;
    LocalDate birthday;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!email.equals(user.email)) return false;
        if (!login.equals(user.login)) return false;
        if (!name.equals(user.name)) return false;
        return birthday.equals(user.birthday);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + birthday.hashCode();
        return result;
    }
}
