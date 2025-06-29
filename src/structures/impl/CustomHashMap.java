package structures.impl;

import structures.CustomMap;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CustomHashMap<K, V> implements CustomMap<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private Node<K, V>[] table;
    private int size;

    public CustomHashMap() {
        table = new Node[DEFAULT_SIZE];
    }

    @Override
    public Optional<V> put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be a null value");
        }
        int bucketIndex = indexFor(hash(key.hashCode()));
        Node<K, V> newNode = new Node<>(key.hashCode(), key, value, null);
        if (table[bucketIndex] == null) {
            table[bucketIndex] = newNode;
            size++;
        } else {
            Node<K, V> currentNode = table[bucketIndex];
            while (currentNode != null) {
                if (currentNode.hash == newNode.hash
                        && (currentNode.key == newNode.key || currentNode.key.equals(newNode.key))) {
                    V oldValue = currentNode.value;
                    currentNode.value = newNode.value;
                    return Optional.ofNullable(oldValue);
                } else if (currentNode.hasNext()) {
                    currentNode = currentNode.nextNode;
                } else {
                    currentNode.nextNode = newNode;
                    size++;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<V> get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be a null value");
        }
        int bucketIndex = indexFor(hash(key.hashCode()));
        Node<K, V> node = table[bucketIndex];
        while (node != null) {
            if (node.hash == key.hashCode() && (node.key == key || node.key.equals(key))) {
                return Optional.ofNullable(node.value);
            }
            node = node.nextNode;
        }
        return Optional.empty();
    }

    @Override
    public Optional<V> remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key can't be a null value");
        }
        int bucketIndex = indexFor(hash(key.hashCode()));
        Node<K, V> currentNode = table[bucketIndex];
        Node<K, V> prevNode = null;
        while (currentNode != null) {
            if (currentNode.hash == key.hashCode() && (currentNode.key == key || currentNode.key.equals(key))) {
                if (currentNode == table[bucketIndex] && currentNode.nextNode == null) {
                    table[bucketIndex] = null;
                } else if (currentNode == table[bucketIndex]) {
                    table[bucketIndex] = currentNode.nextNode;
                } else if (currentNode.nextNode != null) {
                    prevNode.nextNode = currentNode.nextNode;
                } else {
                    prevNode.nextNode = null;
                }
                size--;
                return Optional.ofNullable(currentNode.value);
            }
            prevNode = currentNode;
            currentNode = currentNode.nextNode;
        }
        return Optional.empty();
    }

    @Override
    public Set<V> values() {
        Set<V> result = new HashSet<>();
        for (Node<K, V> node : table) {
            while (node != null) {
                result.add(node.value);
                node = node.nextNode;
            }
        }
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
    }

    @Override
    public boolean contains(K key) {
        if (key == null) {
            return false;
        }
        int bucketIndex = indexFor(hash(key.hashCode()));
        if (table[bucketIndex] == null) {
            return false;
        } else {
            Node<K, V> currentNode = table[bucketIndex];
            while (currentNode != null) {
                if (currentNode.hash == key.hashCode() && (currentNode.key == key || currentNode.key.equals(key))) {
                    return true;
                }
                currentNode = currentNode.nextNode;
            }
        }
        return false;
    }

    private static int hash(int hashCode) {
        return hashCode ^ hashCode >>> 16;
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }

    private static class Node<K, V> {

        final int hash;
        K key;
        V value;
        Node<K, V> nextNode;

        Node(int hash, K key, V value, Node<K, V> nextNode) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.nextNode = nextNode;
        }

        boolean hasNext() {
            return !(nextNode == null);
        }

    }
}
