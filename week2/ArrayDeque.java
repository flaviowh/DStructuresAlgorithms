package week2;
import java.util.Arrays;
import java.util.Iterator;

// DOESN'T WORK YET
public class ArrayDeque<Item> implements Iterable<Item> {
    private int head;
    private int tail;
    private int N;
    private int headSpace;
    private int backSpace;
    private Item[] arr;

    // construct an empty deque
    public ArrayDeque() {
        head = 1;
        tail = 1;
        headSpace = 1;
        backSpace = 1;
        N = 0;
        arr = (Item[]) new Object[1 + headSpace + backSpace];

    }

    // is the deque empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    private void resize(boolean isIncrease, boolean isHead) {
        if (isIncrease) {
            if (isHead) {
                headSpace = N * 2;
                head += headSpace;
                tail += headSpace;
            } else {
                backSpace = N * 2;
                head += backSpace;
                tail += backSpace;
            }
        } else {
            if (isHead) {
                headSpace = N / 2 > 1? N/ 2: 1;
                head -= headSpace;
                tail -= headSpace;
            } else {
                backSpace = N / 2 > 1? N/ 2: 1;
                tail -= backSpace;
                head -= backSpace;
            }
        }
        Item[] newArray = (Item[]) new Object[N + headSpace + backSpace];
//        System.out.println("resizing to : " + ( N + headSpace + backSpace) + ".  N is " + N + " headspace is "  + headSpace + " backspace is " + backSpace);
        for(int b = 0; b < headSpace; b++){
            newArray[b] = null;
        }
        for (int i = headSpace; i < headSpace + N + backSpace - 1; i++) {
            newArray[i] = arr[i - headSpace];
        }
        arr = newArray;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (N++ == 0) {
            arr[head] = item;
        } else {
            arr[--head] = item;
            if (--headSpace == 0){
                resize(true, true);
            N++;
        }}
    }

    public void peek() {
        System.out.println(Arrays.toString(arr));
    }

    // add the item to the back
    public void addLast(Item item) {
        if (N++ == 0) {
            arr[head] = item;
        } else {
            arr[++tail] = item;
            if (--backSpace == 0)
                resize(true, false);
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item outgoing = arr[head];
        arr[head--] = null;
        if (N == (N + headSpace) / 4)
            resize(false, true);
        N--;
        return outgoing;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item outgoing = arr[tail];
        arr[tail--] = null;
        if (N == (N + backSpace) / 4)
            resize(false, false);
        N--;
        return outgoing;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Item current = arr[head];

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            /* not supported */ }

        public Item next() {
            Item outgoing = removeFirst();
            current = arr[head];
            return outgoing;
        }

    }

    

    // unit testing (required)
    public static void main(String[] args) {
        ArrayDeque dodo = new ArrayDeque<Integer>();

        dodo.peek();
        dodo.addFirst(1);
        dodo.peek();
        dodo.addFirst(3);
        
        //dodo.addFirst(4);

        dodo.peek();
        dodo.removeFirst();
        dodo.peek();
        dodo.removeFirst();
        dodo.peek();
        dodo.removeFirst();
        dodo.peek();

        dodo.addFirst(5);
        dodo.peek();
        //dodo.addLast(52);
        dodo.peek();

    }

}