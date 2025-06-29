package structures;

import java.util.Optional;
import java.util.Set;

public interface CustomMap<K, V> {

    Optional<V> put(K key, V value);

    Optional<V> remove(K key);

    Optional<V> get(K key);

    Set<V> values();

    int size();

    void clear();

    boolean contains(K key);

}
