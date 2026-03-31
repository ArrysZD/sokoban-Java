package sokoban.Controleur;

import sokoban.Vue.NiveauGraphique;

public class AnimationCoup extends Animation {
    private int ligneDepart;
    private int colonneDepart;
    private int ligneArrivee;
    private int colonneArrivee;
    private int etape;
    private final int nbEtapes = 10;
    private NiveauGraphique vue;

    public AnimationCoup(int ld, int cd, int la, int ca, NiveauGraphique vue) {
        super(null);
        this.ligneDepart = ld;
        this.colonneDepart = cd;
        this.ligneArrivee = la;
        this.colonneArrivee = ca;
        this.etape = 0;
        this.vue = vue;

        float dx = colonneDepart - colonneArrivee;
        float dy = ligneDepart - ligneArrivee;
        vue.setDecalage(colonneArrivee, ligneArrivee, dx, dy);
    }

    @Override
    public boolean avance() {
        etape++;

        float progress = (float) etape / nbEtapes;
        float dx = (colonneDepart - colonneArrivee) * (1 - progress);
        float dy = (ligneDepart - ligneArrivee) * (1 - progress);

        if (etape >= nbEtapes) {
            vue.supprimeDecalage(colonneArrivee, ligneArrivee);
            return false;
        }

        vue.setDecalage(colonneArrivee, ligneArrivee, dx, dy);
        return true;
    }
}