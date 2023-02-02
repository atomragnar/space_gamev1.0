package gamecharacters;
import java.util.Random;

import static gameutils.Constants.*;

public class EnemyUFO extends Enemy {

    private int side;

    public EnemyUFO() {
        super(new Random().nextInt(46, 54), CUT_OFF_ROW);
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
