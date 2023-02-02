package gamecharacters;

import static gameutils.Constants.*;
import gameutils.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Enemy {

    public static Set<Position> GameState = new HashSet<>();

    Position position;

    public Enemy(int x, int y) {
        this.position = new Position(x, y);
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public abstract void move();

}
