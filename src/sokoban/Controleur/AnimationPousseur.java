package sokoban.Controleur;

public class AnimationPousseur {
    private int direction;
    private int frame;
    private int cmpt;
    private final int vitesse = 8;

    public AnimationPousseur() {
        this.direction = 0;
        this.frame = 0;
        this.cmpt = 0;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public int getFrame() {
        return frame;
    }

    public boolean avance() {
        cmpt++;
        if (cmpt >= vitesse) {
            cmpt = 0;
            frame = (frame + 1) % 4;
        }
        return true;
    }
}