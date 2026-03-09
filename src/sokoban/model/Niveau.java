package sokoban.model;
public class Niveau {
    private char[][] monTableau;
    private String nomNiveau;
    private int nblignes;
    private int nbcolonnes;

    public Niveau(int nblignes, int nbcolonnes) {
        this.nblignes = nblignes;
        this.nbcolonnes = nbcolonnes;
        this.monTableau = new char[nblignes][nbcolonnes];
    }

    public void fixeNom(String nom) {
        this.nomNiveau = nom;
    }

    public void videCase(int ligne, int colonne) {
        this.monTableau[ligne][colonne] = ' ';
    }

    public void ajouteMur(int ligne, int colonne) {
        this.monTableau[ligne][colonne] = '#';
    }

    public void ajouteCaisse(int ligne, int colonne) {
        if (this.monTableau[ligne][colonne] == '.') {
            this.monTableau[ligne][colonne] = '*'; // caisse sur un but
        } else {
            this.monTableau[ligne][colonne] = '$'; // caisse normale
        }
    }

    public void ajoutePousseur(int ligne, int colonne) {
        if (this.monTableau[ligne][colonne] == '.') {
            this.monTableau[ligne][colonne] = '+'; // pousseur sur un but
        } else {
            this.monTableau[ligne][colonne] = '@'; // pousseur normal
        }
    }

    public void ajouteBut(int ligne, int colonne) {
        if (this.monTableau[ligne][colonne] == '$') {
            this.monTableau[ligne][colonne] = '*'; // caisse déjà là
        } else if (this.monTableau[ligne][colonne] == '@') {
            this.monTableau[ligne][colonne] = '+'; // pousseur déjà là
        } else {
            this.monTableau[ligne][colonne] = '.'; // case but vide
        }
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
}