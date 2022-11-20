package week2;

// Question 2
// Stack with max. Create a data structure that efficiently supports the 
//stack operations (push and pop) and also a return-the-maximum operation. 
//Assume the elements are real numbers so that you can compare them.
// Hint: Use two stacks, one to store all of the items and a second stack 
// to store the maximums.

import java.util.Iterator;

public class IntStack implements Iterable<Integer> {
    private int[] s;
    private int N = 0;
    private int max = 0;

    public IntStack(int capacity) {
        s = new int[capacity]; // ugly cast
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(int item) {
        s[N++] = item;
    }

    public int pop() {
        return s[--N];
    }

    public Iterator<Integer> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<Integer> {
        private int index = N;

        public boolean hasNext() {
            return index > 0;
        }

        public void remove() {
            /* not supported */ }

        public Integer next() {
            return s[--index];
        }

    }
}
