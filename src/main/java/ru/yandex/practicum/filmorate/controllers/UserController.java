package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.User;
import service.UserService;
import storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private final UserService userService;
    InMemoryUserStorage userStorage = new InMemoryUserStorage();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") int id,
                          @PathVariable("friendId") int friendId) {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFromFriendList(@PathVariable("id") int id,
                                     @PathVariable("friendId") int friendId) {

        userService.deleteFromFriendList(id, friendId);
    }

    @GetMapping("GET /users/{id}/friends")
    public Set<User> getFriend(@PathVariable("id") int id) {
        return userService.getFriendList(id);
    }

    @GetMapping("GET /users/{id}/friends/common/{otherId}")
    public List<User> commonFriends(@PathVariable("id") int id,
                                    @PathVariable("otherId") int otherId) {
        return userService.returnCommonFriendsList(id, otherId);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) throws WrongInputException {
        return userStorage.create(user);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) throws WrongUpdateException {
        return userStorage.updateUser(user);
    }

}
