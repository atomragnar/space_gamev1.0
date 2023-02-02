package GameProject;

import GameProject.gamecharacters.CelestialEnemy;
import GameProject.gamecharacters.Enemy;
import GameProject.gamecharacters.EnemyUFO;
import GameProject.gamecharacters.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Main implements Runnable {

    public static int counter = 0;
    public static boolean run = false;
    public Thread thread;
    public static View view = null;
    public static Player player = null;
    public static Enemy enemy = null;

    public static List<Enemy> listOfEnemies = null;

    public Main() {

    }
    public static void main(String[] args) throws IOException {
        view = new View();

        Main main = new Main();
        player = new Player();
        boolean gameOn = true;

        listOfEnemies = new ArrayList<>();


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
                Thread.sleep(100);
                if (listOfEnemies.isEmpty()) {
                    GameVariables.isEnemySpawned = false;
                    listOfEnemies = createEnemies(40);
                }
                view.drawScreen(listOfEnemies);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static List<Enemy> createEnemies(int n) {
        LinkedList<Enemy> listOfEnemies = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                listOfEnemies.add(new CelestialEnemy());
            } else {
                listOfEnemies.add(new EnemyUFO());
            }
        }
        return listOfEnemies;
    }


}
