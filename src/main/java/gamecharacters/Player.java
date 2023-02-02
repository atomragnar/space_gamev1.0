package gamecharacters;

import com.googlecode.lanterna.TextCharacter;
import static gameutils.Constants.*;

import com.googlecode.lanterna.TextColor;
import gameutils.Position;

public class Player {

    private final static int PLAYER_START_COLUMN = 45;
    private final static int PLAYER_START_ROW = 35;

    String playerString1 = "   /\\";
    String playerString2 = "  (||)";
    String playerString3 = "  (||)";
    String playerString4 = " /|/\\|\\";
    String playerstring5 = "/_||||_\\";
    String playerstring6 = "    #";
    TextCharacter graphics = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);

    public TextCharacter getGraphics() {
        return graphics;
    }

    private Position position;

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

    public String getPlayerstring6() {
        return playerstring6;
    }

    public Player() {
        this.position = new Position(PLAYER_START_COLUMN, PLAYER_START_ROW);
    }

    public Position getPosition() {
        return position;
    }

    public int getPlayerX() {
        return position.getX();
    }

    public int getPlayerY() {
        return position.getY();
    }

    public void moveLeft() {
        if (position.getX() > 10) {
            int x = position.getX() - 1;
            position.updatePositon(x, position.getY());
        }
    }

    public void moveRight() {
        if (position.getX() < 80) {
            int x = position.getX() + 1;
            position.updatePositon(x, position.getY());
        }
    }

    public void moveUp() {
        if (position.getY() > 5) {
            int y = position.getY() - 1;
            position.updatePositon(position.getX(), y);
        }
    }

    public void moveDown() {
        if (position.getY() < 70) {
            int y = position.getY() + 1;
            position.updatePositon(position.getX(), y);
        }
    }


    public boolean isPlayerHere(int x, int y) {
        return getPlayerX() == x && getPlayerY() == y;
    }


}
