import com.googlecode.lanterna.input.KeyType;

import static com.googlecode.lanterna.input.KeyType.*;

public class Player {

    private final static int PLAYER_START_COLUMN = 40;
    private final static int PLAYER_START_ROW = 20;

    private Position position;

    public Player() {
        this.position = new Position(PLAYER_START_COLUMN, PLAYER_START_ROW);
    }

    public int getPlayerX() {
        return position.getX();
    }

    public int getPlayerY() {
        return position.getY();
    }

    public void moveLeft() {
        if (position.getX() > GameVariables.leftLimit) {
            int x = position.getX() - 1;
            position.updatePositon(x, position.getY());
        }
    }

    public void moveRight() {
        if (position.getX() < GameVariables.rightLimit) {
            int x = position.getX() + 1;
            position.updatePositon(x, position.getY());
        }
    }


}
