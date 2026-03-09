package sokoban.structures;

public class FAP<T extends Comparable<T>> {
    private SequenceListe<T> sequence;

    public FAP() {
        sequence = new SequenceListe<>();
    }

    public void insere(T element) {
        sequence.insereQueue(element);
    }

    public T extraitMin() {
        if (estVide()) throw new RuntimeException("FAP vide");
        // trouver le minimum
        Iterateur<T> it = sequence.iterateur();
        T min = it.prochain();
        while (it.aProchain()) {
            T val = it.prochain();
            if (val.compareTo(min) < 0) min = val;
        }
        // supprimer le minimum
        it = sequence.iterateur();
        while (it.aProchain()) {
            T val = it.prochain();
            if (val.compareTo(min) == 0) {
                it.supprime();
                break;
            }
        }
        return min;
    }

    public boolean estVide() {
        return sequence.estVide();
    }
}