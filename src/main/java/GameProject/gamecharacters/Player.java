package GameProject.gamecharacters;

import com.googlecode.lanterna.TextCharacter;

import com.googlecode.lanterna.TextColor;
import group_seven.gameutils.Position;
import  GameProject.Main;

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
        int leftLimt = Main.view.getColLeftCutOff();
        if (position.getX() > leftLimt) {
            int x = position.getX() - 1;
            position.updatePositon(x, position.getY());
        }
    }

    public void moveRight() {
        int rightLimit = Main.view.getColRightCutOff();
        if (position.getX() < rightLimit) {
            int x = position.getX() + 1;
            position.updatePositon(x, position.getY());
        }
    }

    public void moveUp() {
        int rowUpLimit = Main.view.getRowTopCutOff();
        if (position.getY() > rowUpLimit) {
            int y = position.getY() - 1;
            position.updatePositon(position.getX(), y);
        }
    }

    public void moveDown() {
        int rows = Main.view.getTerminalSize().getRows();
        if (position.getY() < rows) {
            int y = position.getY() + 1;
            position.updatePositon(position.getX(), y);
        }
    }


    public boolean isPlayerHere(int x, int y) {
        return getPlayerX() == x && getPlayerY() == y;
    }


}
