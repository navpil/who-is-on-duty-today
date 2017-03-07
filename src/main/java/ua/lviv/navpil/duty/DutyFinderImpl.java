package ua.lviv.navpil.duty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DutyFinderImpl implements DutyFinder {

    private final UserDao userDao;

    public DutyFinderImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public String whoIsOnDutyToday(Set<String> allAliases) {

        HashSet<String> newComers = new HashSet<>(allAliases);

        List<User> allUsers = new ArrayList<>(userDao.readUsers());
        List<User> existingUsers = new ArrayList<>();

        for (User user : allUsers) {
            if (allAliases.contains(user.getAlias())) {
                newComers.remove(user.getAlias());
                existingUsers.add(user);
            }
        }

        User onDuty;
        if (existingUsers.isEmpty()) {
            onDuty = User.newcomer(allAliases.iterator().next());
        } else {
            onDuty = existingUsers.get(0);
        }

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
