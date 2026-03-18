public class File {
    public final int capacity = 10;
    int size;
    int[] element;
    int tete;

    public File() {
        size = 0;
        element = new int[capacity];
        tete = 0;
    }

    public boolean estVide() {
        return size == 0;
    }

    public void enfiler(int e) throws FilePleineException {
        if (size == capacity)
            throw new FilePleineException("File pleine");
        element[(tete + size) % capacity] = e;
        size++;
    }

    public void defiler() throws FileVideException {
        if (estVide())
            throw new FileVideException("File vide");
        size--;
        tete = (tete + 1) % capacity;
    }

    public int tete() {
        return element[tete];
    }
}
