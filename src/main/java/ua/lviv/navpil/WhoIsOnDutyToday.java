package ua.lviv.navpil;

import ua.lviv.navpil.duty.DutyFinderImpl;
import ua.lviv.navpil.duty.FileUserDao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhoIsOnDutyToday {

    private static final Logger LOG = Logger.getLogger(WhoIsOnDutyToday.class.getCanonicalName());

    public static void main(String[] args) {
        LOG.log(Level.INFO, "Who is on duty today?");

        boolean testRun = args[0].equals("test");

        Set<String> eaters = new HashSet<>(Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
        DutyFinderImpl dutyFinder = new DutyFinderImpl(new FileUserDao());
        System.out.println(dutyFinder.whoIsOnDutyToday(eaters));
    }
}
