import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyType;

public class GameVariables {

    public static int points = 0;
    public static Character pointsChar = Character.forDigit(points, 10);
    public final static int cutOffRow = 10;
    public static int leftOffset = 0;
    public static int rightOffset = 0;
    public static int leftLimit;
    public static int rightLimit;
    public static int heroPositionRow = 10;

    public static final int enemyStartPositonRow = cutOffRow;

    public static int enemyPositionCol;
    public static int enemyPositionRow;
    public static int columnsss;

    public static boolean isEnemySpawned = false;


    static Player player = new Player(columnsss,20 );

    public GameVariables() {

    }

    public static void movePlayer(KeyType keyType) {
            player.move(keyType);
    }

    public static int getPlayerPosition() {
        return player.getPlayerX();
    }

    public static void moveEnemy() {
        enemyPositionRow++;
    }

    public static void setInitialsLimits(int columns) {
        leftLimit = (int) (columns * 0.30);
        rightLimit = (int) (columns * 0.70);
        columnsss = columns /2;


    }


    public static boolean checkDeath() {
        return (player.getPlayerY() == enemyPositionRow) &&
                (player.getPlayerX() == enemyPositionCol);
    }


}
