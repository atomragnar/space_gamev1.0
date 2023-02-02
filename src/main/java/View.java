import gamecharacters.Enemy;
import gamecharacters.EnemyUFO;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import gameutils.GameGraphics;

import static gameutils.Constants.*;
import static gameutils.GameGraphics.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static gameutils.Constants.*;

// TODO kan behova satta en method for att uppdatera terminalsize lopande.


public class View {

    private int playerPreviousY = 0;
    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    private Terminal terminal;
    private Screen screen;
    private TerminalSize terminalSize;


    public View() throws IOException {
        terminal = null;  // 168 80
        this.terminalSize = new TerminalSize(100, 40);
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
        textGraphics.putString(getMiddleColValue(), 5, String.valueOf(GameVariables.points), SGR.BOLD, SGR.BLINK);
    }


    public void drawScreen(Enemy enemyShip) throws IOException {
        drawSpace();
        screen.refresh();
        drawPointsCount();
        screen.refresh();
        drawHero();
        screen.refresh();
        if (!GameVariables.isEnemySpawned) {
            enemyShip = new EnemyUFO();
            spawnEnemy(enemyShip);
        } else {
            enemyShip.move();
            drawEnemy(enemyShip);
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
        /*screen.setCharacter(Main.player.getPlayerX(), Main.player.getPlayerY(), Main.player.getGraphics());*/
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
            textGraphics.setForegroundColor(TextColor.ANSI.YELLOW);
            textGraphics.putString(x, y + 5, Main.player.getPlayerstring6(), SGR.BOLD);
        }
        playerPreviousY = y;
        if (GameVariables.checkDeath()) {
            printGameOver();
            this.terminal.close();
        }

    }

    public void spawnEnemy(Enemy enemyShip) {
        TextCharacter enemyGraphics = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.MAGENTA_BRIGHT);
        screen.setCharacter(enemyShip.getPositionX(), enemyShip.getPositionY(), enemyGraphics);
        GameVariables.isEnemySpawned = true;

    }

    public void drawEnemy(Enemy enemyShip) {
        TextCharacter enemyGraphics = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.MAGENTA_BRIGHT);
        screen.setCharacter(enemyShip.getPositionX(), enemyShip.getPositionY(), enemyGraphics);
        if (enemyShip.getPositionY() == terminalSize.getRows()) {
            GameVariables.isEnemySpawned = false;
            GameVariables.points++;
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
        List<String> startScreen = GameGraphics.readFile("src/main/java/gameutils/introscreen.txt");
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
        List<String> startScreen = GameGraphics.readFile("src/main/java/gameutils/GameOver.txt");
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
            GameVariables.scaleBorders(terminalSize.getColumns());
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
        for (int i = 0; i < rowCutOffTop; i++) {
            for (int j = 0; j < columns; j++) {
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
        return terminalSize.getColumns() / 5;
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


}





