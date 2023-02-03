package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.exceptions.UserDoesntExsistException;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Component
public class InMemoryUserStorage implements UserStorage{

    private final HashMap<Integer,User> users = new HashMap<>();
    private static int userPersonalId= 1;

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public Collection<User> getAllUsers() {
        return  users.values();
    }

    public User getUserById(@PathVariable("id") int id) throws UserDoesntExsistException {
        if(users.get(id) == null){
            throw new UserDoesntExsistException("Ошибка 404, пользователя не существует");
        }
        return users.get(id);
    }

    @Override
    public User create(User user) throws WrongInputException {
        if (!user.getEmail().contains("@")) {
            throw new WrongInputException("поле email не содержит @");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new WrongInputException("В поле birthday указано будущее время");
        } else if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            user.setId(userPersonalId);
            users.put(userPersonalId,user);

            userPersonalId += 1;
            return user;
        } else {
            user.setId(userPersonalId);
            users.put(userPersonalId,user);

            userPersonalId += 1;
            return user;
        }
    }

    @Override
    public User updateUser(User user) throws WrongUpdateException {
        // данный метод перебирает ключи HashMap users, если находит совпадение - заменяет value ключа на объект user,
        // в ином случае выбрасывает исключение.

        if (user != null ) {

            for (Integer u : users.keySet()) {
                if (!(users.get(u).getId() == user.getId())) {
                    throw new WrongUpdateException("");
                }else{
                    users.put(u, user);
                }
            }
        }
        return user;
    }
}
