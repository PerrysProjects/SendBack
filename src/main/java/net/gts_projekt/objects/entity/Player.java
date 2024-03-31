package net.gts_projekt.objects.entity;

public class Player {
    private double x, y;
    private boolean movingUp, movingLeft, movingDown, movingRight;

    private double speed;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        speed = 0.1;
    }

    public void startMoving(MoveType type) {
        switch(type) {
            case UP -> movingUp = true;
            case LEFT -> movingLeft = true;
            case DOWN -> movingDown = true;
            case RIGHT -> movingRight = true;
        }
    }

    public void stopMoving(MoveType type) {
        switch(type) {
            case UP -> movingUp = false;
            case LEFT -> movingLeft = false;
            case DOWN -> movingDown = false;
            case RIGHT -> movingRight = false;
        }
    }

    public void update() {
        if(movingUp) {
            y -= speed;
        }

        if(movingLeft) {
            x -= speed;
        }

        if(movingDown) {
            y += speed;
        }

        if(movingRight) {
            x += speed;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
