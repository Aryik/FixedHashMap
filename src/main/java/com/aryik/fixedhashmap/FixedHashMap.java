package com.aryik.fixedhashmap;

/**
 * Created by Aryik Bhattacharya on 9/18/2017.
 */
public class FixedHashMap {

    // The total number of objects allowed in the map.
    private final int size;

    // The size of the hashMap array.
    private final int capacity;

    // The number of objects stored in the map.
    private int length;
    private Node[] hashMap;

    private class Node {

        // The Node class is used to create a linked list of Nodes in the case of a hashing collision.
        // Nodes are added at the beginning of the linked list at the hashed index.

        private String key;
        private Object value;
        private Node next;

        private Node(String key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        private boolean hasNext() {
            return next != null;
        }
    }

    public FixedHashMap(int size) {
        this.size = size;
        // Set the capacity to the closest power of 2
        capacity = size << 1;
        hashMap = new Node[capacity]; // How big should this array be?
    }

    public FixedHashMap() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    public boolean set(String key, Object value) {
        if (length == size) {
            // if length == size the map is full
            return false;
        } else {
            int hashCode = hash(key);
            if (hashMap[hashCode] == null) {
                // No collision. Initialize a new node and increment length
                hashMap[hashCode] = new Node(key, value, null);
                ++length;
                return true;
            } else {
                // A hashing collision occurred.
                Node node = hashMap[hashCode];
                while (node.hasNext()) {
                    if (node.key.equals(key)) {
                        // If the key already exists, overwrite the value.
                        node.value = value;
                        return true;
                    }
                    node = node.next;
                }
                // The key does not already exist in the map
                // Should I add this new Node at the beginning or the end of the linked list?
                hashMap[hashCode] = new Node(key, value, hashMap[hashCode]);
                ++length;
                return true;
            }
        }
    }

    public Object get(String key) {
        int hashCode = hash(key);
        if (hashMap[hashCode] == null) {
            return null;
        } else {
            Node node = hashMap[hashCode];
            if (node.key.equals(key)) {
                return node.value;
            } else {
                while (true) {
                    if (node.key.equals(key)) {
                        return node.value;
                    } else if (node.hasNext()) {
                        node = node.next;
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    private int hash(String key) {
        // Utility function to make it easier to change hashing functions in the future.
        System.out.println(key + "\t\t" + Math.abs(key.hashCode()) % capacity);
        return Math.abs(key.hashCode()) % capacity;
    }
}
