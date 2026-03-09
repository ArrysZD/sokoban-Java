package sokoban.structures;

public interface Iterateur<T> {
    boolean aProchain();
    T prochain();
    void supprime();
}