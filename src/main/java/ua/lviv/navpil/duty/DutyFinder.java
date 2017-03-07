package ua.lviv.navpil.duty;

import java.util.Set;

public interface DutyFinder {

    String whoIsOnDutyToday(Set<String> eaterAlias);

}
