package GameProject.gamecharacters;

import GameProject.Main;
import group_seven.gameutils.Position;

import java.util.Random;

import static GameProject.gamecharacters.Enemy.GameState;

public class CelestialEnemy extends Enemy {

    int counter;
    int side;
    Position[] positions;

    public CelestialEnemy() {
        super(new Random().nextInt(Main.view.getColLeftCutOff(), Main.view.getColRightCutOff()), Main.view.getRowTopCutOff(),
                new String[]
                        {" ,-,",
                        "/.(",
                        "\\ {",
                        " `-`"}
        );

        int x = this.getPositionX() + 1;
        int y = this.getPositionY();
        positions = new Position[] { new Position(x, y), new Position(x, y + 1), new Position(x, y + 2), new Position(x, y + 3)};
        this.side = 0;
        addEnemy();
    }

    public void addEnemy() {
        int x = this.getPositionX() + 1;
        int y = this.getPositionY();
        positions = new Position[] { new Position(x, y), new Position(x, y + 1), new Position(x, y + 2), new Position(x, y + 3)};
        GameState.add(this.positions[0]);
        GameState.add(this.positions[1]);
        GameState.add(this.positions[2]);
        GameState.add(this.positions[3]);
    }

    public void removeEnemy() {
        GameState.remove(this.positions[0]);
        GameState.remove(this.positions[1]);
        GameState.remove(this.positions[2]);
        GameState.remove(this.positions[3]);
    }

    @Override
    public void move() {
        side++;
        if (side % 2 == 0) {
//            removeEnemy();
            int x = this.getPositionX() + 2;
            int y = this.getPositionY() + 1;
            this.position.updatePositon(x, y);
//            addEnemy();
        } else {
//            removeEnemy();
            int x = this.getPositionX() - 2;
            int y = this.getPositionY() + 1;
            this.position.updatePositon(x, y);
//            addEnemy();
        }
    }



}
