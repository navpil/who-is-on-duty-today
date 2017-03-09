package ua.lviv.navpil.duty.strategy;

import ua.lviv.navpil.duty.User;

import java.util.HashSet;
import java.util.List;

public class VolunteerDutyStrategy implements DutyStrategy {

    private final String volunteer;

    public VolunteerDutyStrategy(String volunteer) {
        this.volunteer = volunteer;
    }

    @Override
    public User getOnDutyUser(HashSet<String> newComers, List<User> existingUsers, List<User> allUsers) {
        return allUsers.stream()
                .filter(user -> user.getAlias().equals(volunteer))
                .findFirst()
                .orElse(User.newcomer(volunteer));
    }
}
