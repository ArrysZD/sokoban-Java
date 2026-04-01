package sokoban.Controleur;

import sokoban.Vue.NiveauGraphique;

public class AnimationPousseur extends Animation {
    private final NiveauGraphique vue;
    private int direction;
    private int frame;
    private int compteur;
    private final int vitesse = 8;
    private boolean active = true;

    public AnimationPousseur(NiveauGraphique vue) {
        super(null);
        this.vue = vue;
        this.direction = 2;
        this.frame = 0;
        this.compteur = 0;
        this.vue.setImagePousseur(this.vue.getImageAnim(direction, frame));
    }

    public void setDirection(int direction) {
        if (direction < 0 || direction > 3) {
            return;
        }
        this.direction = direction;
        this.frame = 0;
        this.compteur = 0;
        this.vue.setImagePousseur(this.vue.getImageAnim(this.direction, this.frame));
        this.vue.repaint();
    }

    public void setActive(boolean active) {
        this.active = active;
        if (!active) {
            this.frame = 0;
            this.compteur = 0;
            this.vue.setImagePousseur(this.vue.getImageAnim(this.direction, this.frame));
            this.vue.repaint();
        }
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean avance() {
        if (!active) {
            return true;
        }

        compteur++;
        if (compteur >= vitesse) {
            compteur = 0;
            frame = (frame + 1) % 4;
            vue.setImagePousseur(vue.getImageAnim(direction, frame));
        }
        return true;
    }
}