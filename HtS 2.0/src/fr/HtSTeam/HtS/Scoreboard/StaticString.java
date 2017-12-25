package fr.HtSTeam.HtS.Scoreboard;

public class StaticString implements AnimatableString {

    private String string;

    public StaticString(String string) {
        this.string = string;
    }

    @Override
    public String current() {
        return string;
    }

    @Override
    public String previous() {
        return string;
    }

    @Override
    public String next() {
        return string;
    }

}