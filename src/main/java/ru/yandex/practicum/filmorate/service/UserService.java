package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserDoesntExsistException;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@Service
public class UserService {
    @Autowired
    private final InMemoryUserStorage userStorage;

    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public InMemoryUserStorage getUserStorage() {
        return userStorage;
    }


    public void addFriend(int userId, int friendId) throws UserDoesntExsistException {
        if(userStorage.getUsers().get(userId) == null || userStorage.getUsers().get(friendId) == null){
            throw new UserDoesntExsistException("пользователя/ей не существует");
        }
        if(userId < 0 || friendId < 0){
            throw new UserDoesntExsistException("айди меньше нуля");
        }

        userStorage.getUsers().get(userId).getFriends().add(userStorage.getUsers().get(friendId));
        userStorage.getUsers().get(friendId).getFriends().add(userStorage.getUsers().get(userId));

    }



    public void deleteFromFriendList(int id, int friendId) {

        userStorage.deleteFromFriendList(id, friendId);
    }

    public List<User> getFriendList(int id) throws WrongInputException {
        if(userStorage.getUsers().get(id).getFriends().size() == 0){
            throw new WrongInputException("список друзей пуст");
        }
        return new ArrayList<>(userStorage.getUsers().get(id).getFriends());
    }


    public List<User> returnCommonFriendsList(int id, int otherId) throws WrongInputException {

        if(userStorage.getUsers().get(id).getFriends() == null ||
                userStorage.getUsers().get(otherId).getFriends() == null){
            throw new WrongInputException("введены неверные данные");
        }
        Set<User> firstPersonFriends = new HashSet<>(userStorage.getUsers().get(id).getFriends());
        Set<User> secondPersonFriends = new HashSet<>(userStorage.getUsers().get(otherId).getFriends());

        firstPersonFriends.retainAll(secondPersonFriends);



        return new ArrayList<>(firstPersonFriends);
    }
}
