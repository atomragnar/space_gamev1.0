package gamecharacters;

import com.googlecode.lanterna.TextCharacter;
import static gameutils.Constants.*;
import gameutils.Position;

public class Player {

    private final static int PLAYER_START_COLUMN = 45;
    private final static int PLAYER_START_ROW = 18;

    String playerString1 = "                  __|__";
    String playerString2 = "                   _|_";
    String playerString3 = "                  /#_#\\";
    String playerString4 = "               __/#(_)#\\__";
    String playerstring5 = "          ____/_ ======= _\\____";
    String playerstring6 = " ________/#_#\\(_)_______(_)/#_#\\________";
    String playerstring7 = "<___+____#(_)#|#/###_###\\#|#(_)#____+___>";
    String playerstring8 = "  O O O  \\___/ |###(_)###| \\___/ O O O";
    String playerstring9 = "             \\__\\_______/__/";

    private Position position;

    public Player() {
        this.position = new Position(PLAYER_START_COLUMN, PLAYER_START_ROW);
    }

    public String getPlayerstring6() {
        return playerstring6;
    }

    public String getPlayerstring7() {
        return playerstring7;
    }

    public String getPlayerstring8() {
        return playerstring8;
    }

    public String getPlayerstring9() {
        return playerstring9;
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
