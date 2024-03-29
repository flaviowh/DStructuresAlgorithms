package Algorithms_Part_I.week2;
import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    private Node first = null;
    private int size = 0;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        size ++;
    }

    public Item pop() {
        Item item = first.item;
        first = first.next;
        size = size > 0 ? size-- : 0;
        return item;
    }

    public int size(){
        return size;
    }

    @Override
    public String toString() {
        if(first == null) return "[ ]";

        StringBuilder str = new StringBuilder();
        Node cur = first;
        while(cur != null){
            str.append(cur.item + " ");
            cur = cur.next;
        }
        return str.toString();
    }
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            /* not supported */ }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}