package ua.lviv.navpil.duty;

import org.junit.Test;
import ua.lviv.navpil.duty.dao.UserDao;
import ua.lviv.navpil.duty.strategy.QueueDutyStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class DutyFinderImplTest {

    @Test
    public void whoIsOnDutyToday() throws Exception {
        UserDao userDao = createTestUserDao("AAA", "BBB");

        DutyFinderImpl dutyFinder = new DutyFinderImpl(userDao, new QueueDutyStrategy());

        HashSet<String> set = new HashSet<>();
        set.add("BBB");
        set.add("CCC");
        set.add("DDD");
        String onDuty = dutyFinder.whoIsOnDutyToday(set);

        assertEquals("BBB", onDuty);
    }

    @Test
    public void shouldIncrementEatingTimesForExcluded() {
        ListBasedUserDao testUserDao = createTestUserDao("EXCLUDED", "SECOND", "NON-EATING");
        DutyFinderImpl dutyFinder = new DutyFinderImpl(testUserDao, new QueueDutyStrategy());
        String onDuty = dutyFinder.whoIsOnDutyToday(set("SECOND", "NEWCOMER"), set("EXCLUDED", "SECOND", "NEWCOMER"));
        assertEquals("SECOND", onDuty);

        List<User> users = testUserDao.readUsers();
        assertEquals(4, users.size());
        int counter = 0;
        assertUserIs("EXCLUDED", 0, 1, users.get(counter++));
        assertUserIs("NON-EATING", 0, 0, users.get(counter++));
        assertUserIs("NEWCOMER", 0, 1, users.get(counter++));
        assertUserIs("SECOND", 1, 1, users.get(counter));

    }

    private void assertUserIs(String alias, int onDuty, int ate, User user) {
        assertEquals("Alias is wrong", alias, user.getAlias());
        assertEquals("On duty count is wrong for " + alias, onDuty, user.getWasOnDuty());
        assertEquals("Eating count is wrong for " + alias, ate, user.getEatTimes());
    }

    private Set<String> set(String ... aliases) {
        return new HashSet<>(Arrays.asList(aliases));
    }

    private ListBasedUserDao createTestUserDao(String ... aliases) {
        return new ListBasedUserDao(Arrays.stream(aliases).map(User::newcomer).collect(Collectors.toList()));
    }


    private static class ListBasedUserDao implements UserDao {

        private List<User> users;

        public ListBasedUserDao(List<User> users) {
            this.users = users;
        }

        @Override
        public List<User> readUsers() {
            return new ArrayList<>(users);
        }

        @Override
        public void saveUsers(List<User> users) {
            this.users = new ArrayList<>(users);
        }
    }

}