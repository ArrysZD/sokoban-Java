package sokoban.structures;
public interface Sequence<T> {
    void insereTete(T element);
    void insereQueue(T element);
    T extraitTete();
    boolean estVide();
    Iterateur<T> iterateur();
}
