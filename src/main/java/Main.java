import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.TextColor.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;


public class Main implements Runnable {
    public static boolean run = false;
    public Thread thread;
    public static View view = null;

    public Main() {

    }
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        boolean gameOn = true;

        view = new View();
        GameVariables.setInitialsLimits(view.getColumns());

        while (gameOn) {
            if (view.isResizeNeeded()) {
                // hantera resize
            }
            main.start();
            switch (view.getKeyInput()) {
                case ArrowLeft -> GameVariables.moveLeft();
                case ArrowRight -> GameVariables.moveRight();
                case Escape -> gameOn = false;
            }
        }
        view.shutDown();
    }

    public void start() {
        if (run) { return; }
        run = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (run) {
            try {
                Thread.sleep(200);
                view.drawScreen();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
