import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyType;

import java.util.Map;

public class GameVariables {

    Map<Integer, String> GameState;

    public static int points = 0;
    public static Character pointsChar = Character.forDigit(points, 10);


    public static int leftOffset = 0;
    public static int rightOffset = 0;


    public static int enemyPositionCol;
    public static int enemyPositionRow;
    public static boolean isEnemySpawned = false;


    public static void moveEnemy() {
        enemyPositionRow++;
    }


    public static boolean checkDeath() {
        return (Main.player.getPlayerY() == enemyPositionRow) &&
                (Main.player.getPlayerX() == enemyPositionCol);
    }


}
