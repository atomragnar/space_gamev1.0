package gamecharacters;

import static gameutils.Constants.*;
import gameutils.Position;

public abstract class Enemy {

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
