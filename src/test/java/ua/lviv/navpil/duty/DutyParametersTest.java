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
        new JCommander(params, new String[]{"-t", "-r"});
        assertTrue(params.isTestRun());
        assertTrue(params.isRevert());
    }

    @Test
    public void testFalseBooleans() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{});
        assertFalse(params.isTestRun());
        assertFalse(params.isRevert());
    }

    @Test
    public void getEffectiveAliases() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-t", "-a", "aaa", "bbb", "ccc", "--exclude", "bbb"});
        HashSet<String> allAliases = new HashSet<>(Arrays.asList("aaa", "ccc"));

        assertEquals(allAliases, params.getEffectiveAliases());
    }

    @Test
    public void getEffectiveAliasesDoesNotFailOnEmptyExclusions() throws Exception {
        DutyParameters params = new DutyParameters();
        new JCommander(params, new String[]{"-t", "-a", "aaa", "bbb", "ccc"});
        HashSet<String> allAliases = new HashSet<>(Arrays.asList("aaa", "bbb", "ccc"));
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
    
}