package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.WrongInputException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final InMemoryUserStorage userStorage;

    public InMemoryUserStorage getUserStorage() {
        return userStorage;
    }


    public User addFriend(int userid, int friendId) {
        if(userStorage.getUsers().get(userid) != null && userStorage.getUsers().get(friendId) != null){
            userStorage.getUsers().get(userid).setFriends(userStorage.getUsers().get(friendId));
            userStorage.getUsers().get(friendId).setFriends(userStorage.getUsers().get(userid));
        }

        log.info("количество друзей у User " + userStorage.getUsers().get(userid).getFriends().size());

        return userStorage.getUsers().get(userid);
    }

    public void deleteFromFriendList(int id, int friendId) {
        userStorage.getUsers().get(id).getFriends().remove(userStorage.getUsers().get(friendId));
        userStorage.getUsers().get(friendId).getFriends().remove(userStorage.getUsers().get(id));
    }

    public List<User> getFriendList(int id) throws WrongInputException {
        if(userStorage.getUsers().get(id).getFriends().size() == 0){
            throw new WrongInputException("список друзей пуст");
        }
        return new ArrayList<>(userStorage.getUsers().get(id).getFriends());
    }


    public List<User> returnCommonFriendsList(int id, int otherId) {
        Set<User> firstPersonFriends = new HashSet<>(userStorage.getUsers().get(id).getFriends());
        Set<User> secondPersonFriends = new HashSet<>(userStorage.getUsers().get(otherId).getFriends());

        HashMap<User, Integer> peopleList = new HashMap<>();
        for (User u : firstPersonFriends) {
            if (peopleList.containsKey(u)) {
                peopleList.put(u, peopleList.get(u) + 1);
            }
            peopleList.put(u, 1);
        }
        for (User u : secondPersonFriends) {
            if (peopleList.containsKey(u)) {
                peopleList.put(u, peopleList.get(u) + 1);
            }
            peopleList.put(u, 1);
        }

        List<User> commonFriendsList = new ArrayList<>();

        for (User u : peopleList.keySet()) {
            if (peopleList.get(u) > 1) {
                commonFriendsList.add(u);
            }
        }
        return commonFriendsList;
    }
}
