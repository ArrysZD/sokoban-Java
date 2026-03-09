package sokoban.utils;

public class SequenceTableau {

    private int[] tab;
    private int tete;  // indice du premier élément
    private int queue; // indice du prochain emplacement libre
    private int size;  // nb d'éléments
    private void agrandir() {
    int[] nouveau = new int[tab.length * 2];
    for (int i = 0; i < size; i++) {
        nouveau[i] = tab[(tete + i) % tab.length];
    }
    tab = nouveau;
    tete = 0;
    queue = size;
}
    public SequenceTableau(int capacite) {
        if (capacite <= 0) throw new IllegalArgumentException("capacité invalide");
        tab = new int[capacite];
        tete = 0;
        queue = 0;
        size = 0;
    }

    public void insereTete(int element) {
        if (size == tab.length) agrandir();
        // décale tout à droite
        tete = (tete - 1 + tab.length) % tab.length;

        tab[tete] = element;
        size++;
    }

    public void insereQueue(int element) {
        if (size == tab.length) agrandir();
        tab[queue] = element;
        queue = (queue + 1) % tab.length;
        size++;
    }

    public int extraitTete() {
        if (estVide()) throw new RuntimeException("Séquence vide");
        int v = tab[tete];
        tete = (tete + 1) % tab.length;
        size--;
        return v;
    }

    public boolean estVide() {
        return size == 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(tab[(tete + i) % tab.length]);
            if (i + 1 < size) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
