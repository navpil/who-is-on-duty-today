package ua.lviv.navpil.duty;

public class User {

    private final String alias;
    private final int wasOnDuty;
    private final int eatTimes;
    private final int problems;

    public User(String alias, int wasOnDuty, int eatTimes, int problems) {
        this.alias = alias;
        this.wasOnDuty = wasOnDuty;
        this.eatTimes = eatTimes;
        this.problems = problems;
    }

    public static User newcomer(String alias){
        return new User(alias, 0, 0, 0);
    }

    public static User parseString(String s) {
        String[] split = s.split(" ");

        String alias = split[0];
        int wasOnDuty = split.length > 1 ? Integer.parseInt(split[1]) : 0;
        int eatTimes = split.length > 2 ? Integer.parseInt(split[2]) : 0;
        int problems = split.length > 3 ? Integer.parseInt(split[3]) : 0;


        return new User(alias, wasOnDuty, eatTimes, problems);
    }

    public String asString() {
        return getAlias() + " " + getWasOnDuty() + " " + getEatTimes() + " " + getProblems();
    }

    public String getAlias() {
        return alias;
    }

    public int getWasOnDuty() {
        return wasOnDuty;
    }

    public int getEatTimes() {
        return eatTimes;
    }

    public int getProblems() {
        return problems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (wasOnDuty != user.wasOnDuty) return false;
        if (eatTimes != user.eatTimes) return false;
        return alias != null ? alias.equals(user.alias) : user.alias == null;

    }

    @Override
    public int hashCode() {
        int result = alias != null ? alias.hashCode() : 0;
        result = 31 * result + wasOnDuty;
        result = 31 * result + eatTimes;
        return result;
    }

    public User onDuty() {
        return new User(alias, wasOnDuty + 1, eatTimes, problems);
    }

    public User ate() {
        return new User(alias, wasOnDuty, eatTimes + 1, problems);
    }
}
