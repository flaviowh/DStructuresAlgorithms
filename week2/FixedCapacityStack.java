package week2;
import java.util.Iterator;

public class FixedCapacityStack<Item> implements Iterable<Item> {
    private Item[] s;
    private int N = 0;

    public FixedCapacityStack(int capacity) {
        s = (Item[]) new Object[capacity]; // ugly cast
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(Item item) {
        s[N++] = item;
    }

    public Item pop() {
        return s[--N];
    }

    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Item> {
        private int index = N;

        public boolean hasNext() {
            return index > 0;
        }

        public void remove() {
            /* not supported */ }

        public Item next() {
            return s[--index];
        }

    }
}
