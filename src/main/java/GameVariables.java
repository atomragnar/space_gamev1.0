import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class GameVariables {

    public static int points = 0;

    /*public static TextCharacter textPoints = new TextCharacter(points, TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK);*/
    public static Character pointsChar = Character.forDigit(points, 10);
    public final static int cutOffRow = 10;
    public static int leftOffset = 0;
    public static int rightOffset = 0;
    public static int leftLimit;
    public static int rightLimit;
    public static int heroPositionRow1 = 20;
    public static int heroPositionRow2 = 19;
    public static int heroPositionColumn1;
    public static int heroPositionColumn2;

    public static final int enemyStartPositonRow = cutOffRow;

    public static int enemyPositionCol;
    public static int enemyPositionRow;

    public static boolean isEnemySpawned = false;

    private static int previousColumns = 0;

    public static void moveRight() {
        if (heroPositionColumn1 < GameVariables.rightLimit) {
            heroPositionColumn1++;
            heroPositionColumn2++;
        }
    }

    public static void moveLeft() {
        if (heroPositionColumn1 > GameVariables.leftLimit) {
            heroPositionColumn1--;
            heroPositionColumn2--;
        }
    }

    public static void moveEnemy() {
        enemyPositionRow++;
    }


    public static void setInitialsLimits(int columns) {
        leftLimit = (int) (columns * 0.30);
        rightLimit = (int) (columns * 0.70);
        heroPositionColumn1 = columns / 2;
        heroPositionColumn2 = heroPositionColumn1;
        previousColumns = columns;

    }

    public static void scaleBorders(int columns) {
        int scalar = columns / previousColumns;
        leftLimit *= scalar;
        rightLimit *= scalar;
        heroPositionRow1 *= scalar;
        heroPositionRow2 *= scalar;
        heroPositionColumn1 *= scalar;
        heroPositionColumn2 *= scalar;

    }

    public static boolean checkDeath() {
        return (heroPositionRow1 == enemyPositionRow || heroPositionRow2 == enemyPositionRow) &&
                (heroPositionColumn1 == enemyPositionCol);
    }


}
