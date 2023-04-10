package ru.yandex.practicum.filmorate.Dao.Impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserDbStorage implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM user_list", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public int create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USER_LIST")
                .usingGeneratedKeyColumns("ID");
        return simpleJdbcInsert.executeAndReturnKey(user.parameters()).intValue();
    }

    @Override
    public User update(User user) {
        jdbcTemplate.update("UPDATE user_list SET name=?, email=?, login=?, birthday=? WHERE id = ?",
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getBirthday(),
                user.getId());
        return user;
    }

    @Override
    public Boolean deleteFromFriendList(Integer id, Integer friendId) {
        String sql = String.format("DELETE\nFROM friend_status\n " +
                "WHERE user_id = %d AND friend_id = %d", id, friendId);
        return jdbcTemplate.update(sql) > 0;
    }

    public Optional<User> getUserById(Integer id){
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * " +
                "FROM user_list WHERE id = ?", id);
        if (userRows.next()) {
            User user = new User(userRows.getString("EMAIL"),
                    userRows.getString("LOGIN"),
                    userRows.getString("NAME"),
                    userRows.getDate("BIRTHDAY").toLocalDate());
            user.setId(userRows.getInt("ID"));
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    public void addFriend(int userId, int friendId){
        jdbcTemplate.update("INSERT INTO friend_status(user_id, friend_id) VALUES (?,?)", userId, friendId);
    }

    public List<User> getAllFriends(Integer id){
        String sql = String.format("SELECT * FROM user_list AS u\n " +
                "JOIN friend_status AS fs ON u.id = fs.friend_id\n " +
                "WHERE fs.user_id = ?", id);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class),id);
    }

    public List<User> getCommonFriends(Integer userId, Integer friendId) {
        String sql = String.format("SELECT *\n " +
                "FROM user_list AS u\n" +
                " JOIN friend_status AS fs ON u.id = fs.friend_id \n" +
                "WHERE fs.user_id = ? AND fs.friend_id IN\n" +
                "(SELECT friend_id \n" +
                "FROM friend_status \n" +
                "WHERE user_id = ?)", userId, friendId);
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), userId, friendId);
    }

}
