package ua.lviv.navpil.duty;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import ua.lviv.navpil.duty.dao.UserDao;
import ua.lviv.navpil.duty.strategy.QueueDutyStrategy;

import java.util.Arrays;
import java.util.HashSet;

public class DutyFinderImplTest {

    @Test
    public void whoIsOnDutyToday() throws Exception {
        UserDao userDao = EasyMock.createNiceMock(UserDao.class);
        EasyMock.expect(userDao.readUsers()).andReturn(Arrays.asList(User.parseString("AAA 1 1 1"), User.parseString("BBB 0 0")));
        EasyMock.replay(userDao);

        DutyFinderImpl dutyFinder = new DutyFinderImpl(userDao, new QueueDutyStrategy());

        HashSet<String> set = new HashSet<>();
        set.add("BBB");
        set.add("CCC");
        set.add("DDD");
        String onDuty = dutyFinder.whoIsOnDutyToday(set);

        Assert.assertEquals("BBB", onDuty);
    }

}