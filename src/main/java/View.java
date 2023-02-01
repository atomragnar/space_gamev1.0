import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Random;

public class View {


    static DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
    private Terminal terminal;
    private Screen screen;
    private final TextGraphics textGraphics;
    private  TerminalSize terminalSize;

    public View() throws IOException {
        terminal = null;
        terminal = defaultTerminalFactory.createTerminal();
        terminal.clearScreen();
        terminal.setCursorVisible(false);
        //this.textGraphics = terminal.newTextGraphics();
        screen = new TerminalScreen(this.terminal);
        this.textGraphics = screen.newTextGraphics();
        screen.startScreen();
        terminalSize = screen.getTerminalSize();


    }



    public void drawLinesGame() {
        TextCharacter lines = new TextCharacter('|', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        int middle = getColumns() / 2;
        int right = middle + GameVariables.rightOffset;
        int left = middle - GameVariables.leftOffset;
        for (int i = 0; i < terminalSize.getRows(); i++) {
            if (i % 1 == 0) {
                right++;
                left--;
            }
            for (int j = 0; j < terminalSize.getColumns(); j++) {
                if (j > left && j < right) {
                    if (i < GameVariables.cutOffRow) {
                        screen.setCharacter(j, i, new TextCharacter(
                                ' ',
                                TextColor.ANSI.BLACK,
                                TextColor.ANSI.BLACK));
                    } else {
                        screen.setCharacter(j, i, lines);
                    }
                }
            }
        }

    }


    public void drawScreen() throws IOException {
        drawLinesGame();
        drawHero();
        if (!GameVariables.isEnemySpawned) {
            spawnEnemy();
        } else {
            GameVariables.moveEnemy();
            drawEnemy();
        }
        drawBackground();
        screen.refresh();
    }


    public void drawBackground() {
        int middle = getColumns() / 2;
        int right = middle + GameVariables.rightOffset;
        int left = middle - GameVariables.leftOffset;

        for(int i = 0; i < terminalSize.getRows(); i++) {
            if (i % 1 == 0) {
                right++;
                left--;
            }
            for(int j = 0; j < terminalSize.getColumns(); j++) {
                if ((j > left && j < right) && i > GameVariables.cutOffRow) {
                    continue;
                } else if (i == 5 && j == middle) {
                    /*screen.setCharacter(j, i, new TextCharacter(
                            Character.forDigit(GameVariables.points, 10),
                            TextColor.ANSI.WHITE,
                            TextColor.ANSI.BLACK));*/
                    textGraphics.putString(j,i, String.valueOf(GameVariables.points));
                } else if (j % 6 == 0) {
                    screen.setCharacter(j, i, new TextCharacter(
                            '|',
                            TextColor.ANSI.WHITE,
                            TextColor.ANSI.BLACK));
                } /*else if (j % 10 == 0) {
                    screen.setCharacter(j, i, new TextCharacter(
                            '~',
                            TextColor.ANSI.WHITE,
                            TextColor.ANSI.BLACK));
                }*/ else {
                    screen.setCharacter(j, i, new TextCharacter(
                            ' ',
                            TextColor.ANSI.WHITE,
                            TextColor.ANSI.BLACK));
                }
            }
        }
    }

    public void drawHero() throws IOException {
        TextCharacter heroShip = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.YELLOW_BRIGHT);
        screen.setCharacter(GameVariables.getPlayerPosition(), GameVariables.heroPositionRow, heroShip);
        if (GameVariables.checkDeath()) {
            this.terminal.close(); // lägg in Game over etc längre fram
        }

    }

    public void spawnEnemy(){
        int leftLimit = (terminalSize.getColumns() / 2) - GameVariables.cutOffRow;
        int rightLimit = (terminalSize.getColumns() / 2) + GameVariables.cutOffRow;
        int random = new Random().nextInt(leftLimit, rightLimit);
        TextCharacter enemy = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.MAGENTA_BRIGHT);
        screen.setCharacter(random, GameVariables.enemyStartPositonRow, enemy);
        GameVariables.enemyPositionCol = random;
        GameVariables.enemyPositionRow = GameVariables.enemyStartPositonRow;
        GameVariables.isEnemySpawned = true;

    }

    public void drawEnemy() {
        TextCharacter enemy = new TextCharacter('^', TextColor.ANSI.CYAN_BRIGHT, TextColor.ANSI.MAGENTA_BRIGHT);
        screen.setCharacter(GameVariables.enemyPositionCol, GameVariables.enemyPositionRow, enemy);
        if (GameVariables.enemyPositionRow == terminalSize.getRows()) {
            GameVariables.isEnemySpawned = false;
            GameVariables.points++;
        }
    }

    // @TODO kan man anvanda som grund till en Game over skarm som sen skiftar
    public void modifyScreen() throws IOException {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < 5000) {
            // The call to pollInput() is not blocking, unlike readInput()
            if(screen.pollInput() != null) {
                break;
            }
            try {
                Thread.sleep(1);
            }
            catch(InterruptedException ignore) {
                break;
            }
            drawScreen();
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
                || keyStroke.getKeyType() == KeyType.Escape)) {
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

    public int getColumns() {
        return this.terminalSize.getColumns();
    }

    public void setInitialTextGraphics() throws IOException {
        this.textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
        this.textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
        this.terminal.flush();
    }

    // @TODO Hur man kan formatera texter
    public void displayTerminalSize() throws IOException {
        textGraphics.drawLine(5, 4, terminal.getTerminalSize().getColumns() - 1, 4, ' ');
        textGraphics.putString(5, 4, terminal.getTerminalSize().toString(), SGR.BOLD);
    }

    public void putChar(int col, int row, char c,
                        TextColor foreGround, TextColor backGround) {
        try {
            terminal.setCursorPosition(col, row);
            terminal.setForegroundColor(foreGround);
            terminal.setBackgroundColor(backGround);
            terminal.putCharacter(c);
            terminal.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   /* public void setAndDrawSpaceShipLane() throws IOException {
        TextCharacter lines = new TextCharacter(' ', TextColor.ANSI.MAGENTA, TextColor.ANSI.MAGENTA);
        int test = 0;
        int scale = 0;
        for (int i = 0; 0 < gameState.length; i++) {
            if (test == 5) {
                test = 0;
                scale--;
            }
            for (int j = 0; j < gameState[0].length; j++) {
                if (j != leftLimit + Math.abs(scale) || j != rightLimit + scale) {
                    continue;
                } else if (j == leftLimit + Math.abs(scale)) {
                    gameState[i][leftLimit + Math.abs(scale)] = GameVariables.BORDER_VALUE;
                    screen.setCharacter(leftLimit + Math.abs(scale), i, lines);
                    if (i == 0) {
                        GameVariables.leftLimitStartX = leftLimit + Math.abs(scale);
                        GameVariables.leftLimitStartY = i;
                    } else if (i == gameState.length - 1) {
                        GameVariables.leftLimitEndX = leftLimit + Math.abs(scale);
                        GameVariables.leftLimitSEndY = i;
                    }
                } else if (j == rightLimit + scale) {
                    gameState[i][leftLimit + scale] = GameVariables.BORDER_VALUE;
                    screen.setCharacter((rightLimit + scale), i, lines);
                    if (i == 0) {
                        GameVariables.rightLimitStartX = rightLimit + scale;
                        GameVariables.rightLimitStartY = i;
                    } else if (i == gameState.length - 1) {
                        GameVariables.rightLimitEndX = rightLimit + scale;
                        GameVariables.rightLimitSEndY = i;
                    }
                }
            }
            test++;
        }
        *//*textGraphics.drawLine(GameVariables.leftLimitStartX, GameVariables.leftLimitStartY, GameVariables.leftLimitEndX, GameVariables.leftLimitSEndY,
                new TextCharacter(' ', TextColor.RGB.ANSI.MAGENTA_BRIGHT, TextColor.RGB.ANSI.MAGENTA_BRIGHT));
        textGraphics.drawLine(GameVariables.rightLimitStartX, GameVariables.rightLimitStartY, GameVariables.rightLimitEndX, GameVariables.rightLimitSEndY,
                new TextCharacter(' ', TextColor.RGB.ANSI.MAGENTA_BRIGHT, TextColor.RGB.ANSI.MAGENTA_BRIGHT));*//*
        screen.refresh();
    }*/

}
