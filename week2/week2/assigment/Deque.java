package week2.assigment;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node head;
    private Node tail;

    // construct an empty deque
    public Deque() {
        n = 0;
    };

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must be valid.");

        if (n++ == 0) {
            head = new Node();
            head.item = item;
            tail = head;

        } else {
            Node prevHead = head;
            Node newHead = new Node();
            newHead.item = item;
            head = newHead;
            head.next = prevHead;
            prevHead.previous = head;
        }
    }

    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item must be valid.");

        if (n++ == 0) {
            head = new Node();
            head.item = item;
            tail = head;
            return;
        }

        Node newTail = new Node();
        newTail.item = item;
        tail.next = newTail;
        newTail.previous = tail;
        tail = newTail;
    }

    public Item removeFirst() {
        if (n == 0) {
            throw new NoSuchElementException("Can't remove from empty queue.");
        }

        Item output = head.item;
        head.item = null;
        if (n > 1) {
            head.next.previous = null;
            head = head.next;
        }
        n--;
        return output;
    }

    public Item removeLast() {
        if (n == 0) {
            throw new NoSuchElementException("Can't remove from empty queue.");
        }
        Item output = tail.item;
        tail.item = null;

        if (n-- > 1) {
            tail.previous.next = null;
            tail = tail.previous;
        } else {
            head = new Node();
            tail = head;
        }
        return output;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = head;

        public boolean hasNext() {
            if (current == null) {
                return false;
            }

            return true;
        }

        public void remove() {
            throw new UnsupportedOperationException("Removal method is not supported.");
        }

        public Item next() {
            if (current == null || current.item == null) {
                throw new NoSuchElementException("Can't remove from empty queue.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        // Tests
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        int[] arr = { 1, 2, 3, 4, 5 };

        for (int i : arr) {
            deque.addFirst(i);
        }
        Iterator<Integer> iter = deque.iterator();
        StdOut.println(iter.next() == 5);
        StdOut.println(deque.size() == arr.length + 1);
        StdOut.println(deque.isEmpty() == false);
        deque.removeLast();
        StdOut.println(deque.isEmpty() == false);
        StdOut.println(iter.hasNext() == true);
        deque.addLast(1);
        deque.addLast(9);
        deque.addLast(8);
        StdOut.println(deque.removeFirst() == 5);

    }

}
