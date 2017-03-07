package ua.lviv.navpil.duty;

import java.util.List;

public interface UserDao {
    List<User> readUsers();

    void saveUsers(List<User> users);

}
