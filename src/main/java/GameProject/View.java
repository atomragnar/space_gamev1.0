package GameProject;

import GameProject.gamecharacters.CelestialEnemy;
import GameProject.gamecharacters.Enemy;
import GameProject.gamecharacters.EnemyUFO;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import group_seven.gameutils.GameGraphics;

import static group_seven.gameutils.Constants.*;
import static group_seven.gameutils.GameGraphics.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class View {


    private int playerPreviousY = 0;

    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    private Terminal terminal;
    private Screen screen;
    private TerminalSize terminalSize;


    public View() throws IOException {
        terminal = null;  // 168 80
        this.terminalSize = new TerminalSize(168, 66);
        terminal = defaultTerminalFactory
                .setInitialTerminalSize(this.terminalSize)
                .createTerminal();
        terminal.clearScreen(); // #TODO Test om denna veckligen behovs
        terminal.setCursorVisible(false);
        //this.textGraphics = terminal.newTextGraphics();
        screen = new TerminalScreen(this.terminal);
        screen.startScreen();

    }


    public void drawLinesGame() {
        int right = getColRightCutOff();
        int left = getColLeftCutOff();
        for (int i = 0; i < terminalSize.getRows(); i++) {
            if (i % 2 == 0) {
                right++;
                left--;
            }
            for (int j = 0; j < terminalSize.getColumns(); j++) {
                drawPointsCount();
                if (i < CUT_OFF_ROW) {
                    int random = new Random().nextInt(left, right);
                    if (random == j) {
                        screen.setCharacter(j, i, MIDDLE_BOX_SPACE);
                    } else {
                        screen.setCharacter(j, i, TextCharAllBlack);
                    }
                } else if (j > left && j < right) {
                    int random = new Random().nextInt(left, right);
                    if (random == j) {
                        screen.setCharacter(j, i, MIDDLE_LINE_SPACE);
                    } else {
                        screen.setCharacter(j, i, linesGraphic);
                    }

                }
            }
        }
    }

    public void drawPointsCount() {
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);

        textGraphics.putString(getMiddleColValue() - 5, 1, "Score:", SGR.BOLD, SGR.BLINK);
        if (GameVariables.points > 9) {
            for (int i = 0; i < 9; i++) {
                textGraphics.putString(getMiddleColValue() - 15, i + 3, Digit.values()[GameVariables.points / 10].getRow(i), SGR.BOLD, SGR.BLINK);
            }
        }
        for (int i = 0; i < 9; i++) {
            textGraphics.putString(getMiddleColValue() - 6, i + 3, Digit.values()[GameVariables.points % 10].getRow(i), SGR.BOLD, SGR.BLINK);
        }
    }


    public void drawScreen(List<Enemy> listOfEnemies) throws IOException {
        drawSpace();
        screen.refresh();
        drawPointsCount();
        screen.refresh();
        drawHero();
        screen.refresh();
        if (GameVariables.isEnemySpawned) {
            int n = 0;
            for (Enemy enemyShip : listOfEnemies) {
                if (Main.counter % 3 == 0) {
                    enemyShip.move();
                } else if (Main.counter % 10 == 0) {
                    enemyShip.move();
                }
            }
            drawEnemy(listOfEnemies);
        } else {
            spawnEnemy(listOfEnemies);
        }
        screen.refresh();
    }


    public void drawBackground() {
        int random;
        int right = getColRightCutOff();
        int left = getColLeftCutOff();

        for (int i = 0; i < terminalSize.getRows(); i++) {
            if (i % 2 == 0) {
                right++;
                left--;
            }
            for (int j = 0; j < terminalSize.getColumns(); j++) {
                if (j <= left) {
                    random = new Random().nextInt(1, left);
                    if (random == j) {
                        screen.setCharacter(j, i, LEFT_SIDE_SPACE);
                    } else {
                        screen.setCharacter(j, i, TextCharAllBlack);
                    }
                } else if (j >= right) {
                    random = new Random().nextInt(right, terminalSize.getColumns());
                    if (random == j) {
                        screen.setCharacter(j, i, RIGHT_SIDE_SPACE);
                    } else {
                        screen.setCharacter(j, i, TextCharAllBlack);
                    }
                }
            }
        }
    }

    public void drawHero() throws IOException {
        /*screen.setCharacter(GameProject.Main.player.getPlayerX(), GameProject.Main.player.getPlayerY(), GameProject.Main.player.getGraphics());*/
        int y = Main.player.getPlayerY();
        int x = Main.player.getPlayerX();
        if (playerPreviousY == 0) {
            playerPreviousY = y;
        }
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        textGraphics.putString(x, y, Main.player.getPlayerString1(), SGR.BOLD);
        textGraphics.putString(x, y + 1, Main.player.getPlayerString2(), SGR.BOLD);
        textGraphics.putString(x, y + 2, Main.player.getPlayerString3(), SGR.BOLD);
        textGraphics.putString(x, y + 3, Main.player.getPlayerString4(), SGR.BOLD);
        textGraphics.putString(x, y + 4, Main.player.getPlayerstring5(), SGR.BOLD);
        if (y < playerPreviousY) {
            textGraphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
            textGraphics.putString(x, y + 5, Main.player.getPlayerstring6(), SGR.BOLD);
        }
        playerPreviousY = y;
        if (GameVariables.checkDeath()) {
            printGameOver();
            this.terminal.close();
        }

    }


    public void spawnEnemy(List<Enemy> listOfEnemies) {

        for (int i = 0; i < 3; i++) {

            Enemy enemyShip = listOfEnemies.get(i);

            if (enemyShip instanceof EnemyUFO) {

                int y = enemyShip.getPositionY();
                int x = enemyShip.getPositionX();

                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);

                for (var enemyString : enemyShip.getEnemyString()) {
                    textGraphics.putString(x, y, enemyString, SGR.BOLD);
                    y++;
                }

            } else if (enemyShip instanceof CelestialEnemy) {

                int y = enemyShip.getPositionY();
                int x = enemyShip.getPositionX();

                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);

                for (var enemyString : enemyShip.getEnemyString()) {
                    textGraphics.putString(x, y, enemyString, SGR.BOLD);
                    y++;
                }
            }
        }
        GameVariables.isEnemySpawned = true;
    }

    public void drawEnemy(List<Enemy> listOfEnemies) {

        for (int i = 0; i < 3; i++) {

            Enemy enemyShip = listOfEnemies.get(i);

            if (enemyShip instanceof EnemyUFO) {

                int y = enemyShip.getPositionY();
                int x = enemyShip.getPositionX();

                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);

                for (var enemyString : enemyShip.getEnemyString()) {
                    textGraphics.putString(x, y, enemyString, SGR.BOLD);
                    y++;
                }

            } else if (enemyShip instanceof CelestialEnemy) {

                int y = enemyShip.getPositionY();
                int x = enemyShip.getPositionX();

                TextGraphics textGraphics = screen.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.YELLOW_BRIGHT);

                for (var enemyString : enemyShip.getEnemyString()) {
                    textGraphics.putString(x, y, enemyString, SGR.BOLD);
                    y++;
                }

            }

            if (enemyShip.getPositionY() == terminalSize.getRows()) {
                listOfEnemies.remove(i);
                GameVariables.points++;
            }
        }
    }


    // @TODO kan man anvanda som grund till en Game over skarm som sen skiftar
    public void modifyScreen() throws IOException {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 5000) {
            // The call to pollInput() is not blocking, unlike readInput()
            if (screen.pollInput() != null) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ignore) {
                break;
            }
        }
    }

    public void printStartScreen() throws IOException {
        terminal.clearScreen();
        screen.refresh();
        List<String> startScreen = GameGraphics.readFile("src/main/java/group_seven/gameutils/introscreen.txt");
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 4000) {
            int row = 5;
            int col = 10;
            TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
            for (String s : startScreen) {
                textGraphics.putString(col, row, s, SGR.BOLD);
                row++;
            }
            screen.refresh();
        }
    }

    public void printGameOver() throws IOException {
        terminal.clearScreen();
        screen.refresh();
        List<String> startScreen = GameGraphics.readFile("src/main/java/group_seven/gameutils/GameOver.txt");
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < 4000) {
            int row = 15;
            int col = 30;
            TextGraphics textGraphics = screen.newTextGraphics();
            textGraphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
            for (String s : startScreen) {
                textGraphics.putString(col, row, s, SGR.BOLD);
                row++;
            }
            screen.refresh();
        }
    }

    /*public void randomPosition() {
        Random random = new Random();
        TerminalPosition cellToModify = new TerminalPosition(
                random.nextInt(terminalSize.getColumns()),
                random.nextInt(terminalSize.getRows()));
    }*/

    public KeyType getKeyInput() throws IOException {
        while (true) {
            try {
                Thread.sleep(15);
                KeyStroke keyStroke = screen.pollInput();
                if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowLeft || keyStroke.getKeyType() == KeyType.ArrowRight
                        || keyStroke.getKeyType() == KeyType.Escape || keyStroke.getKeyType() == KeyType.ArrowUp
                        || keyStroke.getKeyType() == KeyType.ArrowDown)) {
                    return keyStroke.getKeyType();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*public void resizeTerminal() {
        TerminalSize newSize = screen.doResizeIfNecessary();
        if(newSize != null) {
            terminalSize = newSize;
            GameProject.GameVariables.scaleBorders(terminalSize.getColumns());
        }
    }*/
    // @TODO Behover uppdateras sa man helt stanger av spelet
    public void shutDown() throws IOException {
        System.exit(0);
    }

    public void drawTopMiddleLines() {
        int right = getColRightCutOff();
        int left = getColLeftCutOff();
        int middleRow = terminalSize.getRows() / 2;
        for (int i = 0; i < middleRow; i++) {
            right--;
            left++;
            for (int j = left; j < right; j++) {
                int random = new Random().nextInt(left, right);
                if (random == j) {
                    screen.setCharacter(j, i, MIDDLE_LINE_SPACE);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }
        }
    }

    public void drawBottomMiddleLines() {
        int right = getColRightCutOff();
        int left = getColLeftCutOff();
        int start = terminalSize.getRows();
        int middleRowValue = start / 2;
        for (int i = start; i > middleRowValue; i--) {
            right--;
            left++;
            for (int j = left; j < right; j++) {
                int random = new Random().nextInt(left, right);
                if (random == j) {
                    screen.setCharacter(j, i, MIDDLE_LINE_SPACE);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }
        }
    }

    public void drawMiddleMidLeftLines() {
        int rowCutoffTop = getRowTopCutOff();
        int rowCutoffBottom = getRowBottomCutOff();
        int middleCol = getMiddleColValue();

        for (int i = rowCutoffTop; i <= rowCutoffBottom; i++) {
            for (int j = 0; j < middleCol; j++) {
                int random = new Random().nextInt(0, middleCol);
                if (random == j) {
                    screen.setCharacter(j, i, MIDDLE_LEFT);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }

        }
    }

    public void drawMiddleMidRightLines() {
        int columns = terminalSize.getColumns();
        int rowCutOffTop = getRowTopCutOff();
        int rowCutOffBottom = getRowBottomCutOff();
        int middle = getMiddleColValue();

        for (int i = rowCutOffTop; i <= rowCutOffBottom; i++) {
            for (int j = middle; j < columns; j++) {
                int random = new Random().nextInt(middle, columns);
                if (random == j) {
                    screen.setCharacter(j, i, MIDDLE_RIGHT);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }

        }
    }

    public void drawTopLeftCornerLines() {
        int rowCutOffTop = getRowTopCutOff();
        int columns = getColLeftCutOff();
        for (int i = 0; i <= rowCutOffTop; i++) {
            for (int j = 0; j <= columns; j++) {
                int random = new Random().nextInt(0, columns);
                if (random == j) {
                    screen.setCharacter(j, i, RIGHT_SIDE_SPACE);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }
            columns += 2;
        }
    }

    public void drawBottomLeftCorner() {
        int row_cutOffBottom = getRowBottomCutOff();
        int columns = getColLeftCutOff();
        int rows = terminalSize.getRows();
        for (int i = row_cutOffBottom; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int random = new Random().nextInt(0, columns);
                if (random == j) {
                    screen.setCharacter(j, i, LEFT_SIDE_SPACE);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }
            columns += 2;
        }
    }

    public void drawTopRightCorner() {
        int rowCutoffTop = getRowTopCutOff();
        int columns = getColRightCutOff();
        int totalColumns = terminalSize.getColumns();
        for (int i = 0; i < rowCutoffTop; i++) {
            for (int j = columns; j < totalColumns; j++) {
                int random = new Random().nextInt(columns, totalColumns);
                if (random == j) {
                    screen.setCharacter(j, i, LEFT_SIDE_SPACE);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }
            columns -= 2;
        }
    }

    public void drawBottomRightCorner() {
        int totalRows = terminalSize.getRows();
        int totalColumns = terminalSize.getColumns();
        int columns = getColRightCutOff();
        int rowCutOffBottom = getRowBottomCutOff();

        for (int i = totalRows; i > rowCutOffBottom; i--) {
            for (int j = columns; j < totalColumns; j++) {
                int random = new Random().nextInt(columns, totalColumns);
                if (random == j) {
                    screen.setCharacter(j, i, RIGHT_SIDE_SPACE);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }
            columns -= 2;
        }
    }

    public void drawSpace() {
        drawTopMiddleLines();
        drawBottomMiddleLines();
        drawMiddleMidLeftLines();
        drawMiddleMidRightLines();
        drawTopLeftCornerLines();
        drawBottomLeftCorner();
        drawTopRightCorner();
        drawBottomRightCorner();

    }

    public int getColLeftCutOff() {
        // 20% just nu
        return terminalSize.getColumns() / 7;
    }

    public int getColRightCutOff() {
        return terminalSize.getColumns() - getColLeftCutOff();
    }


    public int getRowTopCutOff() {
        // 25% just nu
        return terminalSize.getRows() / 4;
    }

    public int getRowBottomCutOff() {
        return terminalSize.getRows() - getRowTopCutOff();
    }

    public int getMiddleColValue() {
        return terminalSize.getColumns() / 2;
    }

    public TerminalSize getTerminalSize() {
        return terminalSize;
    }
}





