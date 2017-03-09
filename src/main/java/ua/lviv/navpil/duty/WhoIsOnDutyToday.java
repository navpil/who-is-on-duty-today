package ua.lviv.navpil.duty;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import ua.lviv.navpil.duty.dao.FileUserDao;
import ua.lviv.navpil.duty.dao.ReadonlyFileUserDao;
import ua.lviv.navpil.duty.dao.UserDao;
import ua.lviv.navpil.duty.strategy.DutyStrategy;
import ua.lviv.navpil.duty.strategy.QueueDutyStrategy;
import ua.lviv.navpil.duty.strategy.VolunteerDutyStrategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhoIsOnDutyToday {

    private static final Logger LOG = Logger.getLogger(WhoIsOnDutyToday.class.getCanonicalName());

    public static void main(String[] args) throws IOException {
        DutyParameters params = parseParameters(args);
        if (params == null) return;
        String queueFile = params.getQueueFile();
        if (params.isRevert()) {
            if (params.isSoftRun()) {
                LOG.log(Level.INFO, "Reverting queue during soft run does nothing. Please provide --force flag to do actual revert");
            } else {
                LOG.log(Level.INFO, "Reverting previous change");
                revertBackup(queueFile);
            }
        } else {
            LOG.log(Level.INFO, "Who is on duty today?");
            if (params.getEffectiveAliases().isEmpty()) {
                System.out.println("Please provide aliases");
                showUsage();
                return;
            }
            if (params.isSoftRun()) {
                LOG.log(Level.INFO, "This is a soft run, queue will not be updated. Use --force to update the queue");
            } else {
                LOG.log(Level.INFO, "Queue will be updated, use --revert for reverting back");
                backupFile(queueFile);
            }
            UserDao userDao = params.isSoftRun() ? new ReadonlyFileUserDao(queueFile) : new FileUserDao(queueFile);
            DutyStrategy dutyStrategy = params.getVolunteer() == null ? new QueueDutyStrategy() : new VolunteerDutyStrategy(params.getVolunteer());

            DutyFinder dutyFinder = new DutyFinderImpl(userDao, dutyStrategy);
            LOG.log(Level.INFO, dutyFinder.whoIsOnDutyToday(params.getEffectiveAliases(), params.getAllAliases()));
        }
    }

    private static DutyParameters parseParameters(String[] args) throws IOException {
        if (args.length == 0) {
            showUsage();
            return null;
        }
        DutyParameters params = new DutyParameters();
        //Because life is too short to parse command line parameters
        try {
            new JCommander(params, args);
        } catch (ParameterException e) {
            showUsage();
            throw e;
        }
        return params;
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
        JCommander jCommander = new JCommander(new DutyParameters());
        jCommander.setProgramName("java -jar who-is-on-duty-today.jar");
        jCommander.usage();
    }

}
