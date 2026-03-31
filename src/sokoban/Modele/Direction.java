package sokoban.Modele;

public enum Direction {
    HAUT(-1, 0),
    BAS(1, 0),
    GAUCHE(0, -1),
    DROITE(0, 1);

    public final int deltaligne;
    public final int deltacolonne;

    Direction(int dl, int dc) {
        this.deltaligne = dl;
        this.deltacolonne = dc;
    }
}