package sokoban.Modele;

import sokoban.io.LecteurNiveaux;
import java.io.InputStream;

public class Jeu {
    private LecteurNiveaux lecteur;
    private Niveau niveauCourant;
    private int nbPas = 0;
    private int nbPoussees = 0;

    public Jeu(InputStream in) {
        lecteur = new LecteurNiveaux(in);
        prochainNiveau();
    }

    public Niveau niveau() {
        return niveauCourant;
    }

    public int getNbPas() { return nbPas; }
    public int getNbPoussees() { return nbPoussees; }
    public void resetCompteurs() { nbPas = 0; nbPoussees = 0; }

    public boolean prochainNiveau() {
        niveauCourant = lecteur.lisProchainNiveau();
        resetCompteurs();
        return niveauCourant != null;
    }

    public void deplace(int dl, int dc) {
        Niveau n = niveauCourant;
        int lp = n.lignePousseur();
        int cp = n.colonnePousseur();
        int lCible = lp + dl;
        int cCible = cp + dc;

        if (lCible < 0 || lCible >= n.lignes() || cCible < 0 || cCible >= n.colonnes())
            return;

        if (!n.aMur(lCible, cCible) && !n.aCaisse(lCible, cCible)) {
            n.videCase(lp, cp);
            n.ajoutePousseur(lCible, cCible);
            nbPas++;
        } else if (n.aCaisse(lCible, cCible)) {
            int lDerriere = lCible + dl;
            int cDerriere = cCible + dc;
            if (!n.aMur(lDerriere, cDerriere) && !n.aCaisse(lDerriere, cDerriere)) {
                n.videCase(lp, cp);
                n.videCase(lCible, cCible);
                n.ajouteCaisse(lDerriere, cDerriere);
                n.ajoutePousseur(lCible, cCible);
                nbPas++;
                nbPoussees++;
            }
        }
    }
    public Coup deplaceAvecCoup(int dl, int dc) {
    Coup coup = niveauCourant.deplacer(dl, dc);
    if (coup != null) {
        nbPas++;
        if (coup.caisseBougee) nbPoussees++;
    }
    return coup;
}

}