import com.googlecode.lanterna.input.KeyType;

import static com.googlecode.lanterna.input.KeyType.*;

public class Player {

    private Position position;

    public Player(int x, int y) {
        this.position = new Position(x, y);
    }

    public int getPlayerX() {
        return position.getX();
    }

    public int getPlayerY() {
        return position.getY();
    }

    public void move(KeyType keyType) {
        if (keyType == ArrowRight) {
            if (position.getX() < GameVariables.rightLimit) {
                int x = position.getX() + 1;
                position.updatePositon(x, position.getY());
            }
        } else if (keyType == ArrowLeft) {
            if (position.getX() > GameVariables.leftLimit) {
                int x = position.getX() - 1;
                position.updatePositon(x, position.getY());
            }
        }
    }

}
