package ua.lviv.navpil.duty;

import java.util.HashSet;
import java.util.Set;

public interface DutyFinder {

    String whoIsOnDutyToday(Set<String> effectiveAliases, Set<String> allAliases);

    default String whoIsOnDutyToday(HashSet<String> aliases) {
        return whoIsOnDutyToday(aliases, aliases);
    }

}
