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
        // Set the capacity to the next largest power of 2 to ensure that our hash does not ever run into divide by
        // zero exceptions.
        int capacity = 1;
        while (capacity < size) {
            capacity <<= 1;
        }
        this.capacity = capacity;
        hashMap = new Node[capacity]; // How big should this array be?
//        System.out.println("capacity: " + capacity + " \t\t size: " + size);
    }

    public FixedHashMap() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    public boolean set(String key, Object value) {
        int hashCode = hash(key);
        if (hashMap[hashCode] == null) {
            if (length < size) {
                // We cannot replace the nested if statements with && because if hashMap[hashCode] != null we want
                // to deal with the collision but if length < size we want to return false.
                // No collision. Initialize a new node and increment length
                hashMap[hashCode] = new Node(key, value, null);
                ++length;
                return true;
            } else {
                return false;
            }
        } else {
            // A hashing collision occurred.
            Node node = hashMap[hashCode];
            while (true) {
                if (node.key.equals(key)) {
                    // If the key already exists, overwrite the value. Don't increment or check length.
                    node.value = value;
                    return true;
                } else if (node.hasNext()) {
                    node = node.next;
                } else {
                    break;
                }
            }
            // The key does not already exist in the map
            // Should I add this new Node at the beginning or the end of the linked list?
            if (length < size) {
                hashMap[hashCode] = new Node(key, value, hashMap[hashCode]);
                ++length;
                return true;
            } else {
                return false;
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

    public Object delete(String key) {
        int hashCode = hash(key);
        if (hashMap[hashCode] == null) {
            return null;
        } else {
            Node node = hashMap[hashCode];
            if (node.key.equals(key)) {
                if (node.hasNext()) {
                    hashMap[hashCode] = node.next;
                    --length;
                    return node.value;
                } else {
                    hashMap[hashCode] = null;
                    --length;
                    return node.value;
                }
            }
            while (node.hasNext()) {
                Node previous = node;
                node = node.next;
                if (node.key.equals(key)) {
                    if (node.hasNext()) {
                        previous.next = node.next;
                        --length;
                        return node.value;
                    } else {
                        previous.next = null;
                        --length;
                        return node.value;
                    }
                }
            }
            return null;
        }
    }
    private int hash(String key) {
        // Utility function to make it easier to change hashing functions in the future.
//        System.out.println(key + "\t\t" + Math.abs(key.hashCode()) % capacity);
        return Math.abs(key.hashCode()) % capacity;
    }
}
