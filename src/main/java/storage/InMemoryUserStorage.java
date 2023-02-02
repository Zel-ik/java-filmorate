package storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.exceptions.WrongUpdateException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage{

    private final HashMap<Integer,User> users = new HashMap<>();
    private int userid = 0;
    private static int userPersonalId= 1;

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) users.values();
    }

    @Override
    public User create(User user) throws WrongInputException {
        if (!user.getEmail().contains("@")) {
            throw new WrongInputException("поле email не содержит @");
        } else if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new WrongInputException("В поле birthday указано будущее время");
        } else if (user.getName() == null) {
            user.setName(user.getLogin());
            user.setId(userPersonalId);
            users.put(userid,user);

            userPersonalId += 1;
            userid += 1;
            return user;
        } else {
            user.setId(userPersonalId);
            users.put(userid,user);

            userPersonalId += 1;
            userid += 1;
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
