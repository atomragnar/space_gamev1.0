package GameProject.gamecharacters;

import group_seven.gameutils.Position;

import java.util.HashSet;
import java.util.Set;

public abstract class Enemy {

    public static Set<Position> GameState = new HashSet<>();

    Position position;

    private String [] enemyString;

    public Enemy(int x, int y,String [] enemyString) {
        this.position = new Position(x, y);
        this.enemyString = enemyString;
    }

    public String[] getEnemyString() {
        return enemyString;
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public abstract void move();

}
