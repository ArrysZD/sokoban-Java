package sokoban.Modele;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class HistoriqueAPile<T extends Commande> {
    private Deque<T> passe = new ArrayDeque<>();
    private Deque<T> futur = new ArrayDeque<>();

    protected abstract Niveau niveauHistorique();

    public void faire(T commande) {
        if (commande == null) {
            return;
        }
        passe.push(commande);
        futur.clear();
    }

    public void annuler() {
        if (!passe.isEmpty()) {
            T commande = passe.pop();
            commande.desexecuter(niveauHistorique());
            futur.push(commande);
        }
    }

    public void refaire() {
        if (!futur.isEmpty()) {
            T commande = futur.pop();
            commande.executer(niveauHistorique());
            passe.push(commande);
        }
    }

    public boolean peutAnnuler() {
        return !passe.isEmpty();
    }

    public boolean peutRefaire() {
        return !futur.isEmpty();
    }

    public T dernierCoup() {
        return passe.peek();
    }

    public T dernierCoupFutur() {
        return futur.peek();
    }

    public void viderHistorique() {
        passe.clear();
        futur.clear();
    }
}