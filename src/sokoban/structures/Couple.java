package sokoban.structures;

public class Couple<V, P extends Comparable<P>> implements Comparable<Couple<V, P>> {
    private V valeur;
    private P priorite;

    public Couple(V valeur, P priorite) {
        this.valeur = valeur;
        this.priorite = priorite;
    }

    public V valeur() { return valeur; }
    public P priorite() { return priorite; }

    public int compareTo(Couple<V, P> autre) {
        return this.priorite.compareTo(autre.priorite);
    }

    public String toString() {
        return "(" + valeur + ", " + priorite + ")";
    }
}