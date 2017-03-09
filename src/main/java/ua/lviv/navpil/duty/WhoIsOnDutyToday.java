package ua.lviv.navpil.duty;

import com.beust.jcommander.JCommander;
import ua.lviv.navpil.duty.dao.FileUserDao;
import ua.lviv.navpil.duty.dao.ReadonlyFileUserDao;
import ua.lviv.navpil.duty.dao.UserDao;
import ua.lviv.navpil.duty.strategy.DutyStrategy;
import ua.lviv.navpil.duty.strategy.QueueDutyStrategy;
import ua.lviv.navpil.duty.strategy.VolunteerDutyStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhoIsOnDutyToday {

    private static final Logger LOG = Logger.getLogger(WhoIsOnDutyToday.class.getCanonicalName());

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            showUsage();
            return;
        }
        DutyParameters params = new DutyParameters();
        //Because life is too short to parse command line parameters
        new JCommander(params, args);
        String queueFile = params.getQueueFile();
        if (params.isRevert()) {
            LOG.log(Level.INFO, "Reverting previous change");
            revertBackup(queueFile);
        } else {
            LOG.log(Level.INFO, "Who is on duty today?");
            if (params.getEffectiveAliases().isEmpty()) {
                System.out.println("Please provide aliases");
                showUsage();
                return;
            }
            if (!params.isTestRun()) {
                backupFile(queueFile);
            }
            UserDao userDao = params.isTestRun() ? new ReadonlyFileUserDao(queueFile) : new FileUserDao(queueFile);
            DutyStrategy dutyStrategy = params.getVolunteer() == null ? new QueueDutyStrategy() : new VolunteerDutyStrategy(params.getVolunteer());

            DutyFinder dutyFinder = new DutyFinderImpl(userDao, dutyStrategy);
            System.out.println(dutyFinder.whoIsOnDutyToday(params.getEffectiveAliases()));
        }
    }

    private static void revertBackup(String queueFile) throws IOException {
        copyFile(queueFile + ".bak", queueFile);
    }

    private static void backupFile(String queueFile) throws IOException {
        copyFile(queueFile, queueFile + ".bak");
    }

    private static void copyFile(String from, String to) throws IOException {
        File fromFile = new File(from);
        if (fromFile.exists()) {
            Files.copy(Paths.get(fromFile.getPath()), Paths.get(new File(to).getPath()), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void showUsage() throws IOException {
        try(InputStream resource = WhoIsOnDutyToday.class.getClassLoader().getResourceAsStream("usage.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(resource));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

}
