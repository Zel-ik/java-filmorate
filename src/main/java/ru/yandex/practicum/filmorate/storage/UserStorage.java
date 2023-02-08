package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    public Collection<User> getAllUsers();

    public User create( User user) throws WrongInputException;

    public User updateUser( User user) throws WrongUpdateException;

    public void deleteFromFriendList(int id, int friendId);

}
