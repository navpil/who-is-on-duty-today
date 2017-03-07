package ua.lviv.navpil.duty;

import com.beust.jcommander.Parameter;

import java.util.HashSet;
import java.util.Set;

public class DutyParameters {

    @Parameter(names = {"-q", "--queue"})
    private String queueFile = "duty-q.txt";

    @Parameter(names = {"-r", "--revert"})
    private boolean revert;

    @Parameter(names = {"-a", "--alias"}, variableArity = true)
    private Set<String> aliases = new HashSet<>();

    @Parameter(names = {"-t", "--test-run"})
    private boolean testRun;

    @Parameter(names = {"-x", "--exclude"}, variableArity = true)
    private Set<String> exclusions = new HashSet<>();

    public boolean isTestRun() {
        return testRun;
    }

    public boolean isRevert() {
        return revert;
    }

    public Set<String> getEffectiveAliases() {
        HashSet<String> effectiveAliases = new HashSet<>(aliases);
        effectiveAliases.removeAll(exclusions);
        return effectiveAliases;
    }

    public String getQueueFile() {
        return queueFile;
    }
}
