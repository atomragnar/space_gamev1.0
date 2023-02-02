package GameProject;

public enum Line {
    FULL (" ------- "),
    EMPTY("         "),
    LEFT ("|        "),
    RIGHT("        |"),
    BOTH ("|       |");

    private final String text;
    Line(String txt) {
        this.text = txt;
    }

    public String toString() {
        return text;
    }
}
