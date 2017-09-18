package com.aryik.fixedhashmap;

/**
 * Created by Aryik Bhattacharya on 9/18/2017.
 */
public class FixedHashMap {

    // The total number of objects allowed in the map.
    private final int size;

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
        hashMap = new Node[size]; // How big should this array be?
    }

    public FixedHashMap() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    public boolean set(String key, Object value) {
        if (length == size - 1) {
            // if length == size-1 the map is full
            return false;
        } else {
            int hashCode = key.hashCode();
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
}
