package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.Dao.Impl.UserDbStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {

    @Autowired
    private final UserDbStorage userDbStorage;
    @Autowired
    private final InMemoryUserStorage userStorage;

    public UserService(UserDbStorage userDbStorage, InMemoryUserStorage userStorage) {
        this.userDbStorage = userDbStorage;
        this.userStorage = userStorage;
    }

    public InMemoryUserStorage getUserStorage() {
        return userStorage;
    }

    public UserDbStorage getUserDbStorage() {
        return userDbStorage;
    }

    public Collection<User> findAll() {
        return userDbStorage.findAll();
    }
    public int create(User user) {
        return userDbStorage.create(user);
    }

    public User update(User user) {
        correctId(user.getId());
        return userDbStorage.update(user);
    }
    public void addFriend(Integer userId, Integer friendId) {
        correctId(userId);
        correctId(friendId);
        userDbStorage.addFriend(userId, friendId);
    }
    public User getUserById(Integer id){
        return userDbStorage.getUserById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    public void deleteFromFriendList(Integer id, Integer friendId) {
        correctId(id);
        correctId(friendId);
        userDbStorage.deleteFromFriendList(id, friendId);
    }

    public List<User> getFriendList(Integer id){
        correctId(id);
        return userDbStorage.getAllFriends(id);
    }


    public List<User> returnCommonFriendsList(Integer id, Integer otherId){
        correctId(id);
        correctId(otherId);
        return userDbStorage.getCommonFriends(id,otherId);
    }

    private Boolean correctId(Integer id) {
        if (userDbStorage.getUserById(id).isEmpty()) {
            throw new NotFoundException("Id пользователя отсуствует в списке");
        } else {
            return true;
        }
    }
}
