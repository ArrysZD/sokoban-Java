package sokoban.Modele;

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
            n.videCase(lp, cp);
            n.ajoutePousseur(lCible, cCible);
        }

        // Cas 2 : caisse poussable
        else if (n.aCaisse(lCible, cCible)) {
            int lDerriere = lCible + dl;
            int cDerriere = cCible + dc;
            if (!n.aMur(lDerriere, cDerriere) && !n.aCaisse(lDerriere, cDerriere)) {
                n.videCase(lp, cp);
                n.videCase(lCible, cCible);
                n.ajouteCaisse(lDerriere, cDerriere);
                n.ajoutePousseur(lCible, cCible);
            }
        }
    }

    public Coup deplaceAvecCoup(int dl, int dc) {
        return niveauCourant.deplacer(dl, dc);
    }
}