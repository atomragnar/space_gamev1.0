package gamecharacters;

import com.googlecode.lanterna.TextCharacter;
import static gameutils.Constants.*;
import gameutils.Position;

public class Player {

    private final static int PLAYER_START_COLUMN = 45;
    private final static int PLAYER_START_ROW = 33;

    String playerString1 = "   /\\";
    String playerString2 = "  (||)";
    String playerString3 = "  (||)";
    String playerString4 = " /|/\\|\\";
    String playerstring5 = "/_||||_\\";

    String playerstring6 = "";

    private Position position;

    public Player() {
        this.position = new Position(PLAYER_START_COLUMN, PLAYER_START_ROW);
    }

    public String getPlayerString1() {
        return playerString1;
    }

    public String getPlayerString2() {
        return playerString2;
    }

    public String getPlayerString3() {
        return playerString3;
    }

    public String getPlayerString4() {
        return playerString4;
    }

    public String getPlayerstring5() {
        return playerstring5;
    }

    public int getPlayerX() {
        return position.getX();
    }

    public int getPlayerY() {
        return position.getY();
    }

    public void moveLeft() {
        if (position.getX() > LEFT_BORDER_LIMIT - 15) {
            int x = position.getX() - 1;
            position.updatePositon(x, position.getY());
        }
    }

    public void moveRight() {
        if (position.getX() < RIGHT_BORDER_LIMIT + 15) {
            int x = position.getX() + 1;
            position.updatePositon(x, position.getY());
        }
    }

    public boolean isPlayerHere(int x, int y) {
        return getPlayerX() == x && getPlayerY() == y;
    }


}
