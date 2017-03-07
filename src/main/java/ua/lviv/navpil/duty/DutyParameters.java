package ua.lviv.navpil.duty;

import com.beust.jcommander.Parameter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DutyParameters {

    @Parameter(names = {"-n", "--nice"})
    private boolean nice;

    @Parameter(names = {"-q", "--queue"})
    private String queueFile = "duty-q.txt";

    @Parameter(names = {"-r", "--revert"})
    private boolean revert;

    @Parameter(names = {"-a", "--alias"}, variableArity = true)
    private Set<String> aliases = new HashSet<>();

    @Parameter(names = {"-f", "--force"})
    private boolean force;

    @Parameter(names = {"-x", "--exclude"}, variableArity = true)
    private Set<String> exclusions = new HashSet<>();

    public boolean isTestRun() {
        return !force;
    }

    public boolean isRevert() {
        return revert;
    }

    public Set<String> getEffectiveAliases() {
        HashSet<String> effectiveAliases = new HashSet<>(normalize(aliases));
        effectiveAliases.removeAll(normalize(exclusions));
        return effectiveAliases;
    }

    private Set<String> normalize(Set<String> aliases) {
        Stream<String> stringStream = aliases.stream().map(String::toUpperCase);
        if (nice) {
            stringStream = stringStream.flatMap((name) -> {
                Set<String> splitNames = new HashSet<>();
                for(int i = 0; i < name.length(); i += 3) {
                    splitNames.add(name.substring(i, i + 3));
                }
                return splitNames.stream();
            });
        }
        return stringStream.collect(Collectors.toSet());
    }

    public String getQueueFile() {
        return queueFile;
    }
}
