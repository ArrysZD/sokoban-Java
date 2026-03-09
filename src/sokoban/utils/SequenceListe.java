package sokoban.utils;
public class SequenceListe {

    private static class Noeud {
        int val;
        Noeud next;
        Noeud(int v, Noeud n) { val = v; next = n; }
    }

    private Noeud tete;
    private Noeud queue;

    public void insereTete(int element) {
        Noeud n = new Noeud(element, tete);
        tete = n;
        if (queue == null) queue = tete; // si c'était vide
    }

    public void insereQueue(int element) {
        Noeud n = new Noeud(element, null);
        if (estVide()) {
            tete = queue = n;
        } else {
            queue.next = n;
            queue = n;
        }
    }

    public int extraitTete() {
        if (estVide()) throw new RuntimeException("Séquence vide");
        int v = tete.val;
        tete = tete.next;
        if (tete == null) queue = null; // la liste devient vide
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
}
