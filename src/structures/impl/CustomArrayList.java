package structures.impl;

import structures.CustomList;

import java.util.Arrays;
import java.util.Optional;

public final class CustomArrayList<T> implements CustomList<T> {

    private static int capacity = 10;
    private static int size;
    private static int indexNextElement = 0;
    private Object[] array;

    public CustomArrayList() {
        this.array = new Object[capacity];
    }

    public CustomArrayList(int capacity) {
        CustomArrayList.capacity = capacity;
        array = new Object[capacity];
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        increaseCapacityIfFull();
        array[indexNextElement++] = element;
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndexBounds(index);
        return (T) array[index];
    }

    @Override
    public void remove(int index) {
        checkIndexBounds(index);
        array[index] = null;
        moveElementsUp(index);
        size--;
    }

    @Override
    public boolean remove(T element) {
        int elementIndex = -1;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(element)) {
                elementIndex = i;
                break;
            }
        }
        if (elementIndex == -1) {
            return false;
        } else {
            array[elementIndex] = null;
            moveElementsUp(elementIndex);
            size--;
            return true;
        }
    }

    @Override
    public void addAll(Iterable<T> elements) {
        for (T elem : elements) {
            add(elem);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(array, null);
        indexNextElement = 0;
        size = 0;
    }

    private void checkIndexBounds(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Incorrect index:%d, List size:%d", index, size));
        }
    }

    private boolean increaseCapacityIfFull() {
        if (array.length == capacity) {
            int newCapacity = (int) (capacity * 1.5 + 1);
            array = Arrays.copyOf(array, newCapacity);
            capacity = newCapacity;
            return true;
        } else {
            return false;
        }
    }

    private void moveElementsUp(int fromIndex) {
        for (int i = fromIndex; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
    }
}
