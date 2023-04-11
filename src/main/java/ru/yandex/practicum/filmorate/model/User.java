package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@FieldDefaults(makeFinal = true ,level = AccessLevel.PRIVATE)
public class User {
    @NonFinal
    int id;

    @NotEmpty
    @Email
    String email;

    @NotBlank
    @Pattern(regexp = "^\\S*")
    String login;

    @NonFinal
    String name;

    @Past
    LocalDate birthday;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (name == null || name.isEmpty() || name.isBlank()) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("EMAIL", email);
        values.put("LOGIN", login);
        values.put("NAME", name);
        values.put("BIRTHDAY", birthday);
        return values;
    }
}
