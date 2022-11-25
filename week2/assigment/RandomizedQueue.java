package week2.assigment;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] arr;
    private int n;

    // construct an empty randomized queue

    public RandomizedQueue() {

        arr = (Item[]) new Object[INIT_CAPACITY];
        n = 0;

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // resize the underlying array holding the elements
    private Item[] resizeArray(int capacity, Item[] array) {
        assert capacity >= n;

        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = array[i];
        }
        return copy;

    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must be valid.");
        if (n == arr.length)
            arr = resizeArray(2 * arr.length, arr);
        arr[n++] = item;
    }

    private void reshuffle() {
        StdRandom.shuffle(arr);
        int totalSize = arr.length;
        Item[] newArray = (Item[]) new Object[totalSize + 1];
        int i = 0;
        for (Item t : arr) {
            if (t != null) {
                newArray[i++] = t;
            }
        }
        arr = newArray;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) {
            throw new NoSuchElementException("Can't remove from empty queue.");
        }

        int idx = n > 0 ? StdRandom.uniformInt(0, n) : 0;
        Item output = arr[idx];
        arr[idx] = null;
        if(n != arr.length -1){
            Item swap = arr[n-1];
            arr[idx] = swap;
            arr[n-1] = null;
        }
        // shrink size of array if necessary
        if (n-- > 0 && n == arr.length / 4)
            arr = resizeArray(arr.length / 2, arr);
        return output;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) {
            throw new NoSuchElementException("Can't see samples from empty queue.");
        }
        int idx = n > 1 ? StdRandom.uniformInt(0, n ) : 0;
        return arr[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private int i;
        private Item[] iterArray;

        public RandomQueueIterator() {
            i = n - 1;
            iterArray = (Item[]) new Object[n];
            int j = 0;
            for (Item i : arr) {
                if (i != null) {
                    iterArray[j++] = i;
                }
            }
            StdRandom.shuffle(iterArray);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("iterator is empty.");
            }
            return iterArray[i--];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        int[] testInput = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
        for (int n : testInput) {
            rq.enqueue(n);
        }
        StdOut.println(rq.size() == 10);
        StdOut.println(rq.sample());
        int rand = rq.dequeue();
        StdOut.println(rand);
        StdOut.println(rq.size() == 9);
        StdOut.println(rq.isEmpty() == false);

        Iterator<Integer> dodo = rq.iterator();
        for (int j = 0; j < rq.size(); j++) {
            StdOut.println("random iterator: " + dodo.next());
        }
        StdOut.println(dodo.hasNext() == false);
    }

}