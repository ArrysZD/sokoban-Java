package sokoban.model;

import sokoban.io.LecteurNiveaux;
import java.io.InputStream;

public class Jeu {
    private LecteurNiveaux lecteur;
    private Niveau niveauCourant;

    public Jeu(InputStream in) {
        lecteur = new LecteurNiveaux(in);
        prochainNiveau();
    }

    public Niveau niveau() {
        return niveauCourant;
    }

    public boolean prochainNiveau() {
        niveauCourant = lecteur.lisProchainNiveau();
        return niveauCourant != null;
    }
}