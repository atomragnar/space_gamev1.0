package GameProject;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum Digit {
    ZERO (Line.FULL,  Line.BOTH,  Line.EMPTY, Line.BOTH,  Line.FULL ),
    ONE  (Line.EMPTY, Line.RIGHT, Line.EMPTY, Line.RIGHT, Line.EMPTY),
    TWO  (Line.FULL,  Line.RIGHT, Line.FULL,  Line.LEFT,  Line.FULL ),
    THREE(Line.FULL,  Line.RIGHT, Line.FULL,  Line.RIGHT, Line.FULL ),
    FOUR (Line.EMPTY, Line.BOTH,  Line.FULL,  Line.RIGHT, Line.EMPTY),
    FIVE (Line.FULL,  Line.LEFT,  Line.FULL,  Line.RIGHT, Line.FULL ),
    SIX  (Line.EMPTY, Line.LEFT,  Line.FULL,  Line.BOTH,  Line.FULL ),
    SEVEN(Line.FULL,  Line.RIGHT, Line.EMPTY, Line.RIGHT, Line.EMPTY),
    EIGHT(Line.FULL,  Line.BOTH,  Line.FULL,  Line.BOTH,  Line.FULL ),
    NINE (Line.FULL,  Line.BOTH,  Line.FULL,  Line.RIGHT, Line.EMPTY);

    private final Line head, thorax, waist, legs, feet;

    private Digit(Line head, Line thorax, Line waist, Line legs, Line feet) {
        this.head = head;
        this.thorax = thorax;
        this.waist = waist;
        this.legs = legs;
        this.feet = feet;
    }

    public String getRow(int row) {
        switch (row) {
            case 0:
                return head.toString();
            case 1:
            case 2:
            case 3:
                return thorax.toString();
            case 4:
                return waist.toString();
            case 5:
            case 6:
            case 7:
                return legs.toString();
            case 8:
                return feet.toString();
            default:
                throw new IllegalStateException("No such row " + row);
        }
    }

    public String toString() {
        return IntStream.range(0, 9)
                .mapToObj(this::getRow)
                .collect(Collectors.joining("\n"));
    }

    public static void main(String[] args) {
        for (Digit d : Digit.values()) {
            System.out.println("GameProject.Digit : " + d.ordinal());
            System.out.println(d);
        }
    }
}
