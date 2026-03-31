package sokoban.Modele;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class Niveau {
    private char[][] monTableau;
    private String nomNiveau;
    private int nblignes;
    private int nbcolonnes;
    private Map<String, Color> marques = new HashMap<>();

    public Niveau(int nblignes, int nbcolonnes) {
        this.nblignes = nblignes;
        this.nbcolonnes = nbcolonnes;
        this.monTableau = new char[nblignes][nbcolonnes];
    }

    public void fixeNom(String nom) {
        this.nomNiveau = nom;
    }

    public void poseMarque(int ligne, int colonne, Color couleur) {
        marques.put(ligne + "," + colonne, couleur);
    }

    public void retireMarque(int ligne, int colonne) {
        marques.remove(ligne + "," + colonne);
    }

    public Color getMarque(int ligne, int colonne) {
        return marques.get(ligne + "," + colonne);
    }

    public void videMarques() {
        marques.clear();
    }

    public void videCase(int i, int j) {
        if (monTableau[i][j] == '+' || monTableau[i][j] == '*') {
            monTableau[i][j] = '.';
        } else {
            monTableau[i][j] = ' ';
        }
    }

    public void ajouteMur(int ligne, int colonne) {
        this.monTableau[ligne][colonne] = '#';
    }

    public void ajouteCaisse(int ligne, int colonne) {
        if (this.monTableau[ligne][colonne] == '.') {
            this.monTableau[ligne][colonne] = '*';
        } else {
            this.monTableau[ligne][colonne] = '$';
        }
    }

    public void ajoutePousseur(int ligne, int colonne) {
        if (this.monTableau[ligne][colonne] == '.') {
            this.monTableau[ligne][colonne] = '+';
        } else {
            this.monTableau[ligne][colonne] = '@';
        }
    }

    public void ajouteBut(int ligne, int colonne) {
        if (this.monTableau[ligne][colonne] == '$') {
            this.monTableau[ligne][colonne] = '*';
        } else if (this.monTableau[ligne][colonne] == '@') {
            this.monTableau[ligne][colonne] = '+';
        } else {
            this.monTableau[ligne][colonne] = '.';
        }
    }

    public int lignePousseur() {
        for (int i = 0; i < this.nblignes; i++) {
            for (int j = 0; j < this.nbcolonnes; j++) {
                if (this.aPousseur(i, j)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int colonnePousseur() {
        for (int i = 0; i < this.nblignes; i++) {
            for (int j = 0; j < this.nbcolonnes; j++) {
                if (this.aPousseur(i, j)) {
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean estGagne() {
        for (int i = 0; i < this.nblignes; i++) {
            for (int j = 0; j < this.nbcolonnes; j++) {
                if (this.monTableau[i][j] == '$') {
                    return false;
                }
            }
        }
        return true;
    }

    public int lignes() {
        return this.monTableau.length;
    }

    public int colonnes() {
        return this.monTableau[0].length;
    }

    public String nom() {
        return this.nomNiveau;
    }

    public boolean estVide(int ligne, int colonne) {
        return this.monTableau[ligne][colonne] == ' ';
    }

    public boolean aMur(int ligne, int colonne) {
        return this.monTableau[ligne][colonne] == '#';
    }

    public boolean aBut(int ligne, int colonne) {
        return this.monTableau[ligne][colonne] == '.'
            || this.monTableau[ligne][colonne] == '+'
            || this.monTableau[ligne][colonne] == '*';
    }

    public boolean aCaisse(int ligne, int colonne) {
        return this.monTableau[ligne][colonne] == '$'
            || this.monTableau[ligne][colonne] == '*';
    }

    public boolean aPousseur(int ligne, int colonne) {
        return this.monTableau[ligne][colonne] == '@'
            || this.monTableau[ligne][colonne] == '+';
    }

    public char getCase(int ligne, int colonne) {
        return this.monTableau[ligne][colonne];
    }

    public Coup deplacer(int dl, int dc) {
        int l = lignePousseur();
        int c = colonnePousseur();
        int nl = l + dl;
        int nc = c + dc;

        if (nl < 0 || nl >= nblignes || nc < 0 || nc >= nbcolonnes) {
            return null;
        }

        if (aMur(nl, nc)) {
            return null;
        } else if (!aCaisse(nl, nc)) {
            videCase(l, c);
            ajoutePousseur(nl, nc);
            return new Coup(l, c, nl, nc);
        } else {
            int nl2 = nl + dl;
            int nc2 = nc + dc;

            if (nl2 < 0 || nl2 >= nblignes || nc2 < 0 || nc2 >= nbcolonnes) {
                return null;
            }
            if (aMur(nl2, nc2) || aCaisse(nl2, nc2)) {
                return null;
            }

            videCase(nl, nc);
            ajouteCaisse(nl2, nc2);
            videCase(l, c);
            ajoutePousseur(nl, nc);

            Coup coup = new Coup(l, c, nl, nc);
            coup.setCaisse(nl, nc, nl2, nc2);
            return coup;
        }
    }

    @Override
    public Niveau clone() {
        Niveau copie = new Niveau(nblignes, nbcolonnes);
        copie.nomNiveau = this.nomNiveau;

        for (int l = 0; l < nblignes; l++) {
            for (int c = 0; c < nbcolonnes; c++) {
                copie.monTableau[l][c] = this.monTableau[l][c];
            }
        }

        copie.marques = new HashMap<>(this.marques);
        return copie;
    }
}