package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

@Slf4j
@RestController
public class UserController {

    private final HashMap<Integer,User> users = new HashMap<>();
    private int userid = 0;
    private static int userPersonalId= 1;

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return users.values();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws WrongInputException {
        if (user.getLogin().isBlank()) {
            throw new WrongInputException("поле login пустое");
        } else if (user.getEmail().isBlank()) {
            throw new WrongInputException("поле email пустое");
        } else if (!user.getEmail().contains("@")) {
            throw new WrongInputException("поле email не содержит @");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new WrongInputException("В поле birthday указано будущее время");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
            user.setId(userPersonalId);
            users.put(userid,user);

            userPersonalId += 1;
            userid += 1;

            log.info("Размер листа " + users.size());
            return user;
        } else {
            user.setId(userPersonalId);
            users.put(userid,user);

            userPersonalId += 1;
            userid += 1;
            return user;
        }
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws WrongUpdateException {
        // данный метод перебирает ключи HashMap users, если находит совпадение - заменяет value ключа на объект user,
        // в ином случае выбрасывает исключение.

        if (user != null ) {

            for (Integer u : users.keySet()) {
                if (!(users.get(u).getId() == user.getId())) {
                    log.info("Размер листа " + users.size());
                    throw new WrongUpdateException("");
                }else{
                    users.put(u, user);
                }
            }
        }
        return user;
    }
}
