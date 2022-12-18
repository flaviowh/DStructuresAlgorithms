
// Question 2
// Stack with max. Create a data structure that efficiently supports the 
// stack operations (push and pop) and also a return-the-maximum operation. 
// Assume the elements are real numbers so that you can compare them.
// Hint: Use two stacks, one to store all of the items and a second stack 
// to store the maximums.
package Algorithms_Part_I.week2;
import java.util.Iterator;

public class IntStack implements Iterable<Integer> {
    private int[] s;
    private int N = 0;
    private int M = 0;
    private int[] maxes; // actually used an array '-'

    public IntStack(int capacity) {
        maxes = new int[capacity];
        s = new int[capacity];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int max() {
        return maxes[M];
    }

    public void push(int item) {
        s[N++] = item;
        if (item > maxes[M]) {
            maxes[++M] = item;
        }
    }

    public Object pop() {
        if (N == 0) {
            return null;
        }
        int value = s[--N];
        if (value == maxes[M]) {
            maxes[M--] = 0;
        }
        return value;

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
