package sokoban.Controleur;

import sokoban.Modele.AleatoireCoupAi;
import sokoban.Modele.Coup;
import sokoban.Modele.Direction;
import sokoban.Modele.Jeu;
import sokoban.Modele.Niveau;
import sokoban.Vue.NiveauGraphique;

import java.awt.Color;

public class AnimationJeuAutomatique {
    private final AleatoireCoupAi ai;
    private final Jeu jeu;
    private final NiveauGraphique vue;

    private int compteur = 0;
    private static final int INTERVALLE = 30;
    private boolean active = false;

    public AnimationJeuAutomatique(Jeu jeu, NiveauGraphique vue) {
        this.ai = new AleatoireCoupAi();
        this.jeu = jeu;
        this.vue = vue;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void toggleActive() {
        this.active = !this.active;
    }

    public boolean isActive() {
        return active;
    }

    public void avance() {
        if (!active) {
            return;
        }

        compteur++;
        if (compteur < INTERVALLE) {
            return;
        }

        compteur = 0;

        Niveau copie = jeu.niveau().clone();
        Direction dir = ai.retournCoupAi(copie);
        Coup coup = jeu.deplaceAvecCoup(dir.deltaligne, dir.deltacolonne);

        if (coup != null) {
            jeu.niveau().faire(coup);

            jeu.niveau().videMarques();
            coup.ajouteMarque(coup.ligneArrivee, coup.colonneArrivee, Color.RED);
            jeu.niveau().poseMarque(coup.ligneArrivee, coup.colonneArrivee, Color.RED);

            if (jeu.niveau().estGagne()) {
                if (!jeu.prochainNiveau()) {
                    System.exit(0);
                }
            }

            vue.repaint();
        }
    }
}