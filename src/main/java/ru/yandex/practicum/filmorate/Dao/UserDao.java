package ru.yandex.practicum.filmorate.Dao;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDao {

    List<User> findAll();

    int create(User user);

    User update(User user);

    Optional getUserById(Integer id);

    Boolean deleteFromFriendList(Integer userId, Integer friendId);

    List<User> getAllFriends(Integer id);

    List<User> getCommonFriends(Integer userId, Integer friendId);
}
