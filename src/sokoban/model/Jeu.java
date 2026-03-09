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

    public void deplace(int dl, int dc) {
    Niveau n = niveauCourant;
    int lp = n.lignePousseur();
    int cp = n.colonnePousseur();
    int lCible = lp + dl;
    int cCible = cp + dc;

    // Vérifier qu'on ne sort pas de la grille
    if (lCible < 0 || lCible >= n.lignes() || cCible < 0 || cCible >= n.colonnes())
        return;

    // Cas 1 : case cible libre
    if (!n.aMur(lCible, cCible) && !n.aCaisse(lCible, cCible)) {
        n.videCase(lp, cp);        // vider l'ancienne case du pousseur
        n.ajoutePousseur(lCible, cCible); // mettre le pousseur sur la cible
    }

    // Cas 2 : caisse poussable
    else if (n.aCaisse(lCible, cCible)) {
        int lDerriere = lCible + dl;
        int cDerriere = cCible + dc;
        if (!n.aMur(lDerriere, cDerriere) && !n.aCaisse(lDerriere, cDerriere)) {
            n.videCase(lp, cp);           // vider l'ancienne case du pousseur
            n.videCase(lCible, cCible);   // vider la case de la caisse
            n.ajouteCaisse(lDerriere, cDerriere); // mettre la caisse derrière
            n.ajoutePousseur(lCible, cCible);     // mettre le pousseur sur la caisse
        }
    }
}
}