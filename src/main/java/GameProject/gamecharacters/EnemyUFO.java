package GameProject.gamecharacters;
import java.util.Random;

import static group_seven.gameutils.Constants.*;
import GameProject.Main;

public class EnemyUFO extends Enemy {

    private int side;




    public EnemyUFO() {
        super(new Random().nextInt(Main.view.getColLeftCutOff(), Main.view.getColRightCutOff()), Main.view.getRowTopCutOff(),
                new String[]{">|<"});
        this.side = 0;
        addEnemy();

    }

    public void addEnemy() {
        GameState.add(this.position);
    }

    public void removeEnemy() {
        GameState.remove(this.position);
    }

    @Override
    public void move() {
        side++;
        if (side % 2 == 0) {
            removeEnemy();
            int x = this.getPositionX() - 1;
            int y = this.getPositionY() + 1;
            this.position.updatePositon(x, y);
            addEnemy();
        } else {
            removeEnemy();
            int x = this.getPositionX();
            int y = this.getPositionY() + 1;
            this.position.updatePositon(x, y);
            addEnemy();
        }
    }


}
