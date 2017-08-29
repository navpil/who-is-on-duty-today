package ua.lviv.navpil.duty.dao;

import ua.lviv.navpil.duty.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUserDao implements UserDao {

    private static final Logger LOG = Logger.getLogger(FileUserDao.class.getCanonicalName());
    private String fileName;

    public FileUserDao(String fileName) {
        this.fileName = fileName;
    }

    public List<User> readUsers() {
        List<User> users = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                users.add(User.parseString(line));
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Queue file with users not found");
            return Collections.emptyList();
        }
        return users;
    }

    public void saveUsers(List<User> users) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(fileName))) {
            for (User user : users) {
                w.write(user.asString() + "\n");
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Can't write to file");
        }
    }
}
