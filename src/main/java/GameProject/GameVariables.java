package GameProject;

import GameProject.gamecharacters.Enemy;
import group_seven.gameutils.Position;

import java.util.HashMap;
import java.util.Map;

public class GameVariables {
    public static int points = 0;

    public static int enemyPositionCol;
    public static int enemyPositionRow;
    public static boolean isEnemySpawned = false;


    public static void moveEnemy() {
        enemyPositionRow++;
    }


    public static boolean checkDeath() {
        int x = Main.player.getPlayerX() + 4;
        int y = Main.player.getPlayerY();

        Position position1 = new Position(x, y);

        if (Enemy.GameState.contains(position1)) {
            return true;
        }

        Position position2a = new Position(x + 1, y + 1);
        Position position2b = new Position(x - 1, y + 1);

        if (Enemy.GameState.contains(position2a) || Enemy.GameState.contains(position2b)) {
            return true;
        }

        Position position3a = new Position(x + 1, y + 2);
        Position position3b = new Position(x - 1, y + 2);

        if (Enemy.GameState.contains(position3a) || Enemy.GameState.contains(position3b)) {
            return true;
        }

        Position position4a = new Position(x + 2, y + 3);
        Position position4b = new Position(x - 2, y + 3);

        if (Enemy.GameState.contains(position4a) || Enemy.GameState.contains(position4b)) {
            return true;
        }

        Position position5a = new Position(x + 3, y + 4);
        Position position5b = new Position(x - 3, y + 4);

        if (Enemy.GameState.contains(position5a) || Enemy.GameState.contains(position5b)) {
            return true;
        }

        return false;

    }


}
