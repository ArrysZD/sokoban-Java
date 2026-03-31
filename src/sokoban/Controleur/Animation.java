package sokoban.Controleur;

import sokoban.Vue.InterfaceGraphique;

public abstract class Animation {
    protected InterfaceGraphique ig;

    public Animation(InterfaceGraphique ig) {
        this.ig = ig;
    }

    public abstract boolean avance();
}