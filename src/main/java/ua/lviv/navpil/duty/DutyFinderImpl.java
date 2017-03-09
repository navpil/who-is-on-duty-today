package ua.lviv.navpil.duty;

import ua.lviv.navpil.duty.dao.UserDao;
import ua.lviv.navpil.duty.strategy.DutyStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DutyFinderImpl implements DutyFinder {

    private final UserDao userDao;
    private final DutyStrategy dutyStrategy;

    public DutyFinderImpl(UserDao userDao, DutyStrategy dutyStrategy) {
        this.userDao = userDao;
        this.dutyStrategy = dutyStrategy;
    }

    public String whoIsOnDutyToday(Set<String> effectiveAliases, Set<String> allAliases) {

        HashSet<String> newComers = new HashSet<>(effectiveAliases);

        List<User> allUsers = new ArrayList<>(userDao.readUsers());
        List<User> existingUsers = new ArrayList<>();

        for (User user : allUsers) {
            if (effectiveAliases.contains(user.getAlias())) {
                newComers.remove(user.getAlias());
                existingUsers.add(user);
            }
        }

        User onDuty = dutyStrategy.getOnDutyUser(newComers, existingUsers, allUsers);

        for (String alias : newComers) {
            allUsers.add(User.newcomer(alias));
        }

        allUsers.remove(onDuty);
        allUsers.add(onDuty.onDuty());

        saveUsersToFile(allUsers, allAliases);
        return onDuty.getAlias();
    }

    private void saveUsersToFile(List<User> allUsers, Set<String> aliases) {
        List<User> finalList = new ArrayList<>();
        for (User user : allUsers) {
            if (aliases.contains(user.getAlias())) {
                finalList.add(user.ate());
            } else {
                finalList.add(user);
            }
        }
        userDao.saveUsers(finalList);
    }


}
