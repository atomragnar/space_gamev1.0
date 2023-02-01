import java.util.Random;

public class EnemyUFO extends Enemy {
    public EnemyUFO() {
        super(new Random().nextInt(40 - 10, 40 + 10), GameVariables.enemyStartPositonRow);
    }

    @Override
    public void move() {
        int x = this.getPositionX();
        int y = this.getPositionY() + 1;
        this.position.updatePositon(x, y);
    }


}
