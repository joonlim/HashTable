/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashtable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import static tools.FileTools.readFile;

/**
 *
 * search by inputting k. returns v.
 *
 * ex: key = word name. value = word definition.
 *
 *
 * load factor is how full a hash table is allowed to get before the capacity is
 * automatically increased.
 *
 * @author Joon
 */
public class HashTable<K, V> {

    private HashNode<K, V>[] table;

    int size;
    float loadFactor;

    // Constructs a new, empty hashtable with a default initial capacity (11) and load factor (0.75).
    public HashTable() {
        table = new HashNode[11];
        loadFactor = 0.75f;
        this.size = 0;

    }

    // Constructs a new, empty hashtable with the specified initial capacity and default load factor (0.75).
    public HashTable(int initialCapacity) {

        table = new HashNode[initialCapacity];
        loadFactor = 0.75f;
        this.size = 0;

    }

    // Constructs a new, empty hashtable with the specified initial capacity and the specified load factor.
    public HashTable(int initialCapacity, float loadFactor) {

        table = new HashNode[initialCapacity];
        loadFactor = loadFactor;
        this.size = 0;

    }

    // returns the index of the table that the node is put into based on its key.
    private int getIndex(K key) {
        int hash = key.hashCode() % table.length;
        if (hash < 0) {
            hash += table.length;
        }
        return hash;
    }

    // Maps the specified key to the specified value in this hashtable. Returns old value if a value is updated.
    public V insert(K key, V value) {
        int hash = getIndex(key);

        // Check if same key already exists. If so update it with the new value.
        // Ex: Update a word in the dictionary with a new definition.
        for (HashNode<K, V> node = table[hash]; node != null; node = node.next) { // cycle through a linked list contained at at table[hash].
            if ((hash == node.hash) && key.equals(node.key)) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }
        // increase size and load factor. if size / table.length is greater than load factor, increase the table capacity.
        size++;
        if (size / table.length > loadFactor) {
            resize(table.length * 2);
        }

        // Add new node to the start of the linked list at table[hash] position.
        HashNode< K, V> node = new HashNode<K, V>(key, value, table[hash], hash); // linked to node at table[hash]
        table[hash] = node;

        return null;
    }

    /**
     * Removes the given key from hash table. 1. Gets the hash using built in
     * hash code method and by doing % to match to local index 2. Look for the
     * corresponding index in nodes array, if found then, we got the linked list
     * a. Search for the given key, also match the hash b. If found, make
     * previous.next = node.next; and return true.
     *
     * @param key
     * @return removed the value?
     */
    public boolean remove(K key) {
        int hash = getIndex(key);
        HashNode<K, V> previous = null;
        for (HashNode<K, V> node = table[hash]; node != null; node = node.next) {
            if ((hash == node.hash) && key.equals(node.key)) {
                if (previous != null) {
                    previous.next = node.next;
                } else {
                    table[hash] = node.next;
                }
                return true;
            }
            previous = node;
        }
        return false;
    }

    /**
     * Using a key receive its value. ex: using a word find its definition.
     *
     * @param key
     * @return
     */
    public V get(K key) {
        int hash = getIndex(key);

        for (HashNode<K, V> node = table[hash]; node != null; node = node.next) {
            if (key.equals(node.key)) {
                return node.value;
            }
        }
        return null;
    }

    /**
     * Automatically resize when the size / table.length is greater than the
     * load factor.
     *
     * @param size
     */
    public void resize(int newSize) {
        HashTable<K, V> newTable = new HashTable<K, V>(newSize, loadFactor);
        for (HashNode<K, V> node : table) {
            for (; node != null; node = node.next) {
                newTable.insert(node.key, node.value);
                this.remove(node.key);
            }
        }
        table = newTable.table;
    }

    public void printTable() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) {
                System.out.println(i + "    empty");
            } else {
                System.out.print(i + "    " + table[i].key);
                HashNode<K, V> next = table[i].next;
                while (next != null) {
                    System.out.print(",   " + next.key);
                    next = next.next;
                }
                System.out.println("");
            }
        }
    }

    /**
     * check if table has no keys
     *
     * @return table is empty
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    static class HashNode<K, V> {

        final K key;
        V value;
        HashNode<K, V> next;
        final int hash;

        public HashNode(K k, V v, HashNode<K, V> nextNode, int hash) {
            key = k;
            value = v;
            next = nextNode;
            this.hash = hash;
        }
    }

    /**
     * In this test the program reads a text file with stores information onto
     * <code>Record</code> objects. Then each object is stored in a hash table
     * with the student name on the record as its key.
     *
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws IOException, FileNotFoundException {

        Scanner input = new Scanner(System.in);

        HashTable records = new HashTable(13);

        // example path: ./src/hashtable/res/records.txt
        System.out.print("Enter name of record file: ");
        String fileName = input.next();

        String data = readFile(fileName);

        while (data != null) {
            try {
                int indexOfNewLine = data.indexOf("\n");
                String name = data.substring(0, indexOfNewLine);

                data = data.substring(indexOfNewLine + 1);
                indexOfNewLine = data.indexOf("\n");
                String idNumber = data.substring(0, indexOfNewLine);

                data = data.substring(indexOfNewLine + 1);
                indexOfNewLine = data.indexOf("\n");
                int rank = Integer.parseInt(data.substring(0, indexOfNewLine));

                data = data.substring(indexOfNewLine + 1);
                indexOfNewLine = data.indexOf("\n");
                double gpa = Double.parseDouble(data.substring(0, indexOfNewLine));

                data = data.substring(indexOfNewLine + 1);
                indexOfNewLine = data.indexOf("\n");
                String major = data.substring(0, indexOfNewLine);

                Record newRecord = new Record(name, idNumber, rank, gpa, major);

                records.insert(name, newRecord);

                data = data.substring(indexOfNewLine + 2);

            } catch (StringIndexOutOfBoundsException e) {
                data = null;
            }
        } // END OF WHILE LOOP

        records.printTable();

        // file name: ./src/hashtable/res/records.txt
        input.nextLine();
        while (true) {
            System.out.print("\nEnter name of student: ");

            String name = input.nextLine();

            String output = (records.get(name) == null) ? "There is no student by that name" : records.get(name).toString();

            System.out.print(output);
        }

    }

}
