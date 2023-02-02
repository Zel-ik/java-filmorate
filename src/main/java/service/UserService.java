package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import storage.InMemoryUserStorage;

import java.util.*;

@Service
public class UserService {

    InMemoryUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Set<User> returnALlFriend(User user) {
        return user.getFriends();
    }

    public User addFriend(int userid, int friendId) {
        userStorage.getUsers().get(userid).setFriends(userStorage.getUsers().get(friendId));
        userStorage.getUsers().get(friendId).setFriends(userStorage.getUsers().get(userid));

        return userStorage.getUsers().get(userid);
    }

    public void deleteFromFriendList(int id, int friendId) {
        userStorage.getUsers().get(id).getFriends().remove(userStorage.getUsers().get(friendId));
        userStorage.getUsers().get(friendId).getFriends().remove(userStorage.getUsers().get(id));
    }

    public Set<User> getFriendList(int id) {
        return userStorage.getUsers().get(id).getFriends();
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
