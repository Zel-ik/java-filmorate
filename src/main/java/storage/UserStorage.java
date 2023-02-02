package storage;

import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public List<User> getAllUsers();

    public User create( User user) throws WrongInputException;

    public User updateUser( User user) throws WrongUpdateException;

}
