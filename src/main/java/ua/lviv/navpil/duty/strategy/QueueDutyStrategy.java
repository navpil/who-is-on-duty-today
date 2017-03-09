package ua.lviv.navpil.duty.strategy;

import ua.lviv.navpil.duty.User;

import java.util.HashSet;
import java.util.List;

public class QueueDutyStrategy implements DutyStrategy {

    @Override
    public User getOnDutyUser(HashSet<String> newComers, List<User> existingUsers, List<User> allUsers) {
        if (existingUsers.isEmpty()) {
            return User.newcomer(newComers.iterator().next());
        } else {
            return existingUsers.get(0);
        }
    }
}