package ua.lviv.navpil.duty;

import com.beust.jcommander.JCommander;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DutyParametersTest {
    @Test
    public void testTrueBooleans() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-r"});
        assertTrue(params.isTestRun());
        assertTrue(params.isRevert());
    }

    @Test
    public void testFalseBooleans() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-f"});
        assertFalse(params.isTestRun());
        assertFalse(params.isRevert());
    }

    @Test
    public void getEffectiveAliases() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-a", "AAA", "bbb", "ccc", "DDD", "--exclude", "BBB", "ddd"});
        HashSet<String> allAliases = new HashSet<>(Arrays.asList("AAA", "CCC"));

        assertEquals(allAliases, params.getEffectiveAliases());
    }

    @Test
    public void getEffectiveAliasesDoesNotFailOnEmptyExclusions() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-a", "aaa", "bbb", "ccc"});
        HashSet<String> allAliases = new HashSet<>(Arrays.asList("AAA", "BBB", "CCC"));
        assertEquals(allAliases, params.getEffectiveAliases());
    }

    @Test
    public void getNiceAliases() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-n", "-a", "aaabbb", "cccDDD", "   sdfadfffe"});
        HashSet<String> allAliases = new HashSet<>(Arrays.asList("AAA", "BBB", "CCC", "DDD", "SDF", "ADF", "FFE"));
        assertEquals(allAliases, params.getEffectiveAliases());
    }

    @Test
    public void getNotNiceAliases() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-a", "aaabbb", "cccDDD", "   sdfadfffe"});
        HashSet<String> allAliases = new HashSet<>(Arrays.asList("AAABBB", "CCCDDD", "SDFADFFFE"));
        assertEquals(allAliases, params.getEffectiveAliases());
    }

    @Test
    public void readDutyQ() {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-q", "test-q.txt"});
        assertEquals("test-q.txt", params.getQueueFile());
    }

    @Test
    public void readNoDutyQ() {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{});
        assertEquals("duty-q.txt", params.getQueueFile());
    }

    @Test
    public void readVolunteer() {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"--volunteer", "kho"});
        assertEquals("KHO", params.getVolunteer());
    }

    @Test
    public void readNoVolunteer() {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{});
        assertEquals(null, params.getVolunteer());
    }

}