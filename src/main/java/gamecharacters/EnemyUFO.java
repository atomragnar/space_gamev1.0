package gamecharacters;
import java.util.Random;

import static gameutils.Constants.*;

public class EnemyUFO extends Enemy {

    private int side;

    public EnemyUFO() {
        super(new Random().nextInt(46, 54), CUT_OFF_ROW);
        this.side = 0;
    }


    @Override
    public void move() {
        side++;
        if (side % 2 == 0) {
            int x = this.getPositionX() - 1;
            int y = this.getPositionY() + 1;
            this.position.updatePositon(x, y);
        } else {
            int x = this.getPositionX();
            int y = this.getPositionY() + 1;
            this.position.updatePositon(x, y);
        }
    }


}
