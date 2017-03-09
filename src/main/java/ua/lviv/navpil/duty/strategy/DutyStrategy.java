package ua.lviv.navpil.duty.strategy;

import ua.lviv.navpil.duty.User;

import java.util.HashSet;
import java.util.List;

public interface DutyStrategy {
    User getOnDutyUser(HashSet<String> newComers, List<User> existingUsers, List<User> allUsers);
}
