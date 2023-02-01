public abstract class Enemy {

    Position position;

    public Enemy(int x, int y) {
        this.position = new Position(x, y);
    }

    public int getPositionX() {
        return position.getX();
    }

    public int getPositionY() {
        return position.getY();
    }

    public abstract void move();

}
