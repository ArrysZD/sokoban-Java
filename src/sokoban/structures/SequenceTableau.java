package sokoban.structures;

    public class SequenceTableau<T> implements Sequence<T> {


    private T[] tab;
    private int tete;  // indice du premier élément
    private int queue; // indice du prochain emplacement libre
    private int size;  // nb d'éléments
    @SuppressWarnings("unchecked")
    private void agrandir() {
    T[] nouveau = (T[]) new Object[tab.length * 2];
    for (int i = 0; i < size; i++) {
        nouveau[i] = tab[(tete + i) % tab.length];
    }
    tab = nouveau;
    tete = 0;
    queue = size;
}
    @SuppressWarnings("unchecked")
    public SequenceTableau(int capacite) {
        if (capacite <= 0) throw new IllegalArgumentException("capacité invalide");
        tab = (T[]) new Object[capacite];
        tete = 0;
        queue = 0;
        size = 0;
    }

    public void insereTete(T element) {
        if (size == tab.length) agrandir();
        // décale tout à droite
        tete = (tete - 1 + tab.length) % tab.length;

        tab[tete] = element;
        size++;
    }

    public void insereQueue(T element) {
        if (size == tab.length) agrandir();
        tab[queue] = element;
        queue = (queue + 1) % tab.length;
        size++;
    }

    public T extraitTete() {
        if (estVide()) throw new RuntimeException("Séquence vide");
        T v = tab[tete];
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
    public Iterateur<T> iterateur() {
    return new IterateurSequenceTableau();
}

private class IterateurSequenceTableau implements Iterateur<T> {
    private int indice;       // position courante (0 à size-1)
    private int dernierIndice; // indice du dernier lu
    private boolean peutSupprimer;

    IterateurSequenceTableau() {
        indice = 0;
        dernierIndice = -1;
        peutSupprimer = false;
    }

    public boolean aProchain() {
        return indice < size;
    }

    public T prochain() {
        if (!aProchain()) throw new RuntimeException("Plus d'éléments");
        dernierIndice = indice;
        indice++;
        peutSupprimer = true;
        return tab[(tete + dernierIndice) % tab.length];
    }

    public void supprime() {
        if (!peutSupprimer) throw new IllegalStateException("supprime() sans prochain()");
        // décaler les éléments après dernierIndice vers la gauche
        for (int i = dernierIndice; i < size - 1; i++) {
            tab[(tete + i) % tab.length] = tab[(tete + i + 1) % tab.length];
        }
        queue = (queue - 1 + tab.length) % tab.length;
        size--;
        indice--;
        peutSupprimer = false;
    }
}
}
