package ua.lviv.navpil.duty;

import java.util.List;

public class ReadonlyFileUserDao extends FileUserDao {

    public ReadonlyFileUserDao(String fileName) {
        super(fileName);
    }

    @Override
    public void saveUsers(List<User> users) {
        //do nothing
    }
}
