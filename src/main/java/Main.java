import gamecharacters.Enemy;
import gamecharacters.EnemyUFO;
import gamecharacters.Player;

import java.io.IOException;


public class Main implements Runnable {
    public static boolean run = false;
    public Thread thread;
    public static View view = null;
    public static Player player = null;
    public static Enemy enemy = null;

    public Main() {

    }
    public static void main(String[] args) throws IOException {

        Main main = new Main();
        player = new Player();
        boolean gameOn = true;


        view = new View();

        view.printStartScreen();
        while (gameOn) {
            main.start();
            switch (view.getKeyInput()) {
                case ArrowLeft  -> player.moveLeft();
                case ArrowRight -> player.moveRight();
                case ArrowDown -> player.moveDown();
                case ArrowUp -> player.moveUp();
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
                Thread.sleep(30);
                if (enemy == null || !GameVariables.isEnemySpawned) {
                    enemy = new EnemyUFO();
                }
                view.drawScreen(enemy);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
