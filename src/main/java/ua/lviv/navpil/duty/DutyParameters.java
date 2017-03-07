package ua.lviv.navpil.duty;

import com.beust.jcommander.Parameter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        HashSet<String> effectiveAliases = new HashSet<>(toUpperCase(aliases));
        effectiveAliases.removeAll(toUpperCase(exclusions));
        return effectiveAliases;
    }

    private Set<String> toUpperCase(Set<String> aliases) {
        return aliases.stream().map(String::toUpperCase).collect(Collectors.toSet());
    }

    public String getQueueFile() {
        return queueFile;
    }
}
