package structures;

public interface CustomList<T> {

    boolean add(T element);

    T get(int index);

    void remove(int index);

    boolean remove(T element);

    void addAll(Iterable<T> elements);

    int size();

    void clear();
}
