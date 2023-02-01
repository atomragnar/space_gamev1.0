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

import static gameutils.Constants.*;
import static gameutils.GameGraphics.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import static gameutils.Constants.*;

// TODO kan behova satta en method for att uppdatera terminalsize lopande.


public class View {


    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    private Terminal terminal;
    private Screen screen;
    private TerminalSize terminalSize;


    public View() throws IOException {
        terminal = null;
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
        int right = RIGHT_BORDER_LIMIT;
        int left = LEFT_BORDER_LIMIT;
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
        textGraphics.putString(MIDDLE, 5, String.valueOf(GameVariables.points), SGR.BOLD, SGR.BLINK);
    }


    public void drawScreen(Enemy enemyShip) throws IOException {
        drawSpace();
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
        int right = RIGHT_BORDER_LIMIT;
        int left = LEFT_BORDER_LIMIT;

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
        //TextCharacter heroShip = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
        //screen.setCharacter(Main.player.getPlayerX(), Main.player.getPlayerY(), heroShip);
        int y = Main.player.getPlayerY();
        int x = Main.player.getPlayerX();
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        textGraphics.putString(x, y, Main.player.getPlayerString1(), SGR.BOLD);
        textGraphics.putString(x, y + 1, Main.player.getPlayerString2(), SGR.BOLD);
        textGraphics.putString(x, y + 2, Main.player.getPlayerString3(), SGR.BOLD);
        textGraphics.putString(x, y + 3, Main.player.getPlayerString4(), SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        textGraphics.putString(x, y + 4, Main.player.getPlayerstring5(), SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        textGraphics.putString(x, y + 5, Main.player.getPlayerstring6(), SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        textGraphics.putString(x, y + 6, Main.player.getPlayerstring7(), SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.RED_BRIGHT);
        textGraphics.putString(x, y + 7, Main.player.getPlayerstring8(), SGR.BOLD);
        textGraphics.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        textGraphics.putString(x, y + 8, Main.player.getPlayerstring9(), SGR.BOLD);
        if (GameVariables.checkDeath()) {
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

    /*public void randomPosition() {
        Random random = new Random();
        TerminalPosition cellToModify = new TerminalPosition(
                random.nextInt(terminalSize.getColumns()),
                random.nextInt(terminalSize.getRows()));
    }*/


    // @TODO hur man kan skapa box i skarmen eventuellt for poang rakning etc
    /*public void drawBox() throws IOException {

        String sizeLabel = "Terminal Size: " + terminalSize;
        TerminalPosition labelBoxTopLeft = new TerminalPosition(1, 1);
        TerminalSize labelBoxSize = new TerminalSize(sizeLabel.length() + 2, 3);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);
        TextGraphics textGraphics = screen.newTextGraphics();
        //This isn't really needed as we are overwriting everything below anyway, but just for demonstrative purpose
        textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(1),
                labelBoxTopLeft.withRelativeRow(2).withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);
        textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(1), Symbols.DOUBLE_LINE_VERTICAL);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(2), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
        textGraphics.putString(labelBoxTopLeft.withRelative(1, 1), sizeLabel);
        screen.refresh();
        Thread.yield();

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
        int right = COLUMN_CUTOFF_RIGHT;
        int left = COLUMN_CUTOFF_LEFT;
        for (int i = 0; i < MIDDLE_ROW_VALUE; i++) {
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
        int right = COLUMN_CUTOFF_RIGHT;
        int left = COLUMN_CUTOFF_LEFT;
        int start = terminalSize.getRows();
        for (int i = start; i > MIDDLE_ROW_VALUE; i--) {
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
        for (int i = ROW_CUTOFF_TOP; i <= ROW_CUTOFF_BOTTOM; i++) {
            for (int j = 0; j < MIDDLE; j++) {
                int random = new Random().nextInt(0, MIDDLE);
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
        for (int i = ROW_CUTOFF_TOP; i <= ROW_CUTOFF_BOTTOM; i++) {
            for (int j = MIDDLE; j < columns; j++) {
                int random = new Random().nextInt(MIDDLE, columns);
                if (random == j) {
                    screen.setCharacter(j, i, MIDDLE_RIGHT);
                } else {
                    screen.setCharacter(j, i, TextCharAllBlack);
                }
            }

        }
    }

    public void drawTopLeftCornerLines() {
        int columns = COLUMN_CUTOFF_LEFT;
        for (int i = 0; i < ROW_CUTOFF_TOP; i++) {
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
        int columns = COLUMN_CUTOFF_LEFT;
        int rows = terminalSize.getRows();
        for (int i = ROW_CUTOFF_BOTTOM; i < rows; i++) {
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
        int columns = COLUMN_CUTOFF_RIGHT;
        int totalColumns = terminalSize.getColumns();
        for (int i = 0; i < ROW_CUTOFF_TOP; i++) {
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
        int columns = COLUMN_CUTOFF_RIGHT;
        for (int i = totalRows; i > ROW_CUTOFF_BOTTOM; i--) {
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

}





