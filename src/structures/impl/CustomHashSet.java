package structures.impl;

public class CustomHashSet<T> {

    private final CustomHashMap<T, Object> hashMap;

    public CustomHashSet() {
        this.hashMap = new CustomHashMap<>();
    }

    public void add(T element) {
        hashMap.put(element, null);
    }

    public void remove(T element) {
        hashMap.remove(element);
    }

    public int size() {
        return hashMap.size();
    }
}
