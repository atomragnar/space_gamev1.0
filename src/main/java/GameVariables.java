import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyType;

import java.util.HashMap;
import java.util.Map;

public class GameVariables {

    Map<Integer, String> GameState;

    public static int points = 10;

    public static final Map<Integer,String> pointsCharacterTable = new HashMap<>(){{
        put(1,"ONE");
        put(2,"TWO");
        put(3,"3");
        put(4,"4");
        put(5,"5");
        put(6,"6");
        put(7,"7");
        put(8,"8");
        put(9,"9");
        put(0,"0");
    }};


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
