package gamecharacters;

import com.googlecode.lanterna.TextCharacter;
import static gameutils.Constants.*;

import com.googlecode.lanterna.TextColor;
import gameutils.Position;

public class Player {

    private final static int PLAYER_START_COLUMN = 45;
    private final static int PLAYER_START_ROW = 35;

    /*String playerString1 = "                  __|__";
    String playerString2 = "                   _|_";
    String playerString3 = "                  /#_#\\";
    String playerString4 = "               __/#(_)#\\__";
    String playerstring5 = "          ____/_ ======= _\\____";
    String playerstring6 = " ________/#_#\\(_)_______(_)/#_#\\________";
    String playerstring7 = "<___+____#(_)#|#/###_###\\#|#(_)#____+___>";
    String playerstring8 = "  O O O  \\___/ |###(_)###| \\___/ O O O";
    String playerstring9 = "             \\__\\_______/__/";*/

    TextCharacter graphics = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);

    public TextCharacter getGraphics() {
        return graphics;
    }

    private Position position;

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

/*

//-A-\\
  ___---=======---___
          (=__\   /.. ..\   /__=)
          ---\__O__/---*/
