package ua.lviv.navpil.duty.dao;

import ua.lviv.navpil.duty.User;

import java.util.List;

public interface UserDao {
    List<User> readUsers();

    void saveUsers(List<User> users);

}
