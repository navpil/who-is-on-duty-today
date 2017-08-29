package ua.lviv.navpil.duty;

import com.beust.jcommander.Parameter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DutyParameters {

    @Parameter(names = {"-n", "--nice"}, description = "parses aliases nicely, three letters per alias even if they happen to kludge together (what happens if copypasted from google doc)")
    private boolean nice;

    @Parameter(names = {"-q", "--queue"}, description = "queue file name to use")
    private String queueFile = "duty-q.txt";

    @Parameter(names = {"-r", "--revert"}, description = "revert the queue file")
    private boolean revert;

    @Parameter(names = {"-a", "--alias"}, variableArity = true, description = "required aliases of users who will eat today")
    private Set<String> aliases = new HashSet<>();

    @Parameter(names = {"-f", "--force"}, description = "this will force a change - the current person on duty will be remembered and queue will be changed")
    private boolean force;

    @Parameter(names = {"-x", "--exclude"}, variableArity = true, description = "aliases to exclude from calculation")
    private Set<String> exclusions = new HashSet<>();

    @Parameter(names = {"--volunteer"}, description = "volunteer to be on duty")
    private String volunteer;

    public boolean isSoftRun() {
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

    public String getVolunteer() {
        return volunteer == null ? null : volunteer.toUpperCase();
    }

    public Set<String> getAllAliases() {
        return normalize(aliases);
    }
}
