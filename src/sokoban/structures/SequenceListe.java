package sokoban.structures;

public class SequenceListe<T> implements Sequence<T> {
    private  class Noeud {
        T val;
        Noeud next;
        Noeud(T v, Noeud n) { val = v; next = n; }
    }

    private Noeud tete;
    private Noeud queue;

    public void insereTete(T element) {
        Noeud n = new Noeud(element, tete);
        tete = n;
        if (queue == null) queue = tete;
    }

    public void insereQueue(T  element) {
        Noeud n = new Noeud(element, null);
        if (estVide()) {
            tete = queue = n;
        } else {
            queue.next = n;
            queue = n;
        }
    }

    public T extraitTete() {
        if (estVide()) throw new RuntimeException("Séquence vide");
        T v = tete.val;
        tete = tete.next;
        if (tete == null) queue = null;
        return v;
    }

    public boolean estVide() {
        return tete == null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Noeud cur = tete;
        while (cur != null) {
            sb.append(cur.val);
            cur = cur.next;
            if (cur != null) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public Iterateur<T> iterateur() {
        return new IterateurSequenceListe();
    }
    private class IterateurSequenceListe implements Iterateur<T> {

    private Noeud courant;      // prochain à lire
    private Noeud dernier;      // dernier lu (à supprimer)
    private Noeud avantDernier; // celui avant dernier
    private boolean peutSupprimer;

    IterateurSequenceListe() {
        courant = tete;
        dernier = null;
        avantDernier = null;
        peutSupprimer = false;
    }

    public boolean aProchain() {
        return courant != null;
    }

    public T prochain() {
    if (!aProchain()) throw new RuntimeException("Plus d'éléments");
    avantDernier = dernier;
    dernier = courant;
    courant = courant.next;
    peutSupprimer = true;
    return dernier.val;
}

    public void supprime() {
    if (!peutSupprimer) throw new IllegalStateException("supprime() appelé sans prochain()");
    if (avantDernier == null) {
        // supprimer la tete
        tete = courant;
        if (tete == null) queue = null;
    } else {
        avantDernier.next = courant;
        if (courant == null) queue = avantDernier;
    }
    peutSupprimer = false;
}
}
}