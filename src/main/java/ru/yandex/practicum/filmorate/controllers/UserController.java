package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.UserDoesntExsistException;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id,
                          @PathVariable("friendId") int friendId) throws  UserDoesntExsistException {

        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFromFriendList(@PathVariable("id") int id,
                                     @PathVariable("friendId") int friendId) {

        userService.deleteFromFriendList(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriendList(@PathVariable("id") int id) throws WrongInputException {
        return userService.getFriendList(id);
    }
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getFriendsList(@PathVariable("id") int id,
                                     @PathVariable("otherId") int friendId) throws WrongInputException {
        return userService.returnCommonFriendsList(id, friendId);
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userService.getUserStorage().getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") int id) throws UserDoesntExsistException {
        return userService.getUserStorage().getUserById(id);
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws WrongInputException {
        return userService.getUserStorage().create(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws WrongUpdateException {
        return userService.getUserStorage().updateUser(user);
    }

}
