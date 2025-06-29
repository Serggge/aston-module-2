package structures.impl;

import structures.CustomList;
import java.util.NoSuchElementException;

public class CustomLinkedList<T> implements CustomList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public boolean add(T element) {
        checkOnNullValue(element);
        if (head == null) {
            Node<T> newNode = new Node<>(element, null, null);
            head = newNode;
            tail = newNode;
        } else {
            linkLast(element);
        }
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> currentNode = null;
        int skip = index;
        if (size / 2 > index) {
            currentNode = head;
            while (skip > 0) {
                currentNode = currentNode.next;
                skip--;
            }
        } else {
            currentNode = tail;
            skip = size - index - 1;
            while (skip > 0) {
                currentNode = currentNode.prev;
                skip--;
            }
        }
        return currentNode.data;
    }

    @Override
    public void remove(int index) {
        checkIndex(index);
        int skip = index;
        Node<T> currentNode = null;
        if (size / 2 > index) {
            currentNode = head;
            while (skip > 0) {
                currentNode = currentNode.next;
                skip--;
            }
        } else {
            currentNode = tail;
            skip = size - index - 1;
            while (skip > 0) {
                currentNode = currentNode.prev;
                skip--;
            }
        }
        removeNode(currentNode);
        size--;
    }

    @Override
    public boolean remove(T element) {
        checkOnNullValue(element);
        if (head == null) {
            return false;
        }
        Node<T> currentNode = head;
        boolean isFound = false;
        while (currentNode != null) {
            if (currentNode.data.equals(element)) {
                removeNode(currentNode);
                size--;
                isFound = true;
            }
            currentNode = currentNode.next;
        }
        return isFound;
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
        head = tail = null;
        size = 0;
    }

    private void checkOnNullValue(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Element can't be a null value");
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException(String.format("Incorrect index:%d, List size:%d", index, size));
        } else if (head == null) {
            throw new NoSuchElementException("List is empty");
        }
    }

    private void removeNode(Node<T> node) {
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head.next.prev = null;
            head = head.next;
        } else if (node == tail) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void linkLast(T element) {
        Node<T> newNode = new Node<>(element, null, tail);
        tail.next = newNode;
        tail = newNode;
    }

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
}
