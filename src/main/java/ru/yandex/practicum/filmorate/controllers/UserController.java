package ru.yandex.practicum.filmorate.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController( UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users/{id}/friends")
    public List<User> getFriendList(@PathVariable("id") int id) {
        return userService.getUserDbStorage().getAllFriends(id);
    }
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriendsList(@PathVariable("id") int id,
                                     @PathVariable("otherId") int friendId) {
        return userService.returnCommonFriendsList(id,friendId);
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") int id,
                          @PathVariable("friendId") int friendId){
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFromFriendList(@PathVariable("id") int id,
                                     @PathVariable("friendId") int friendId) {
        userService.deleteFromFriendList(id, friendId);
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public User  create(@Valid @RequestBody User user){
        user.setId(userService.create(user));
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

}
