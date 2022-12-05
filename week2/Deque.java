package week2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

/// A GPT3 inplementation

public class Deque<T> implements Iterable<T> {
    private T[] array;
    private int start;
    private int end;
    private int size;
    private int capacity;

    public Deque(int initialCapacity) {
        this.capacity = initialCapacity;
        this.array = (T[]) new Object[initialCapacity];
        this.start = 0;
        this.end = 0;
        this.size = 0;
    }

    public void pushFront(T value) {
        if (this.size == this.capacity) {
            this.resize(this.capacity * 2);
        }

        // Decrement the start index and insert the new element
        this.start = (this.start - 1 + this.capacity) % this.capacity;
        this.array[this.start] = value;
        this.size++;
    }

    public void pushBack(T value) {
        if (this.size == this.capacity) {
            this.resize(this.capacity * 2);
        }

        // Insert the new element at the end index and increment the end index
        this.array[this.end] = value;
        this.end = (this.end + 1) % this.capacity;
        this.size++;
    }

    public T popFront() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        // Save the element at the start index, increment the start index, and decrement
        // the size
        T value = this.array[this.start];
        this.start = (this.start + 1) % this.capacity;
        this.size--;
        return value;
    }

    public T popBack() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        // Decrement the end index, save the element at the new end index, and decrement
        // the size
        this.end = (this.end - 1 + this.capacity) % this.capacity;
        T value = this.array[this.end];
        this.size--;
        return value;
    }

    public T peekFront() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        return this.array[this.start];
    }

    public T peekBack() {
        if (this.size == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        return this.array[(this.end - 1 + this.capacity) % this.capacity];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    private void resize(int newCapacity) {
        T[] newArray = (T[]) new Object[newCapacity];
        for (int i = 0; i < this.size; i++) {
            int index = (this.start + i) % this.capacity;
            newArray[i] = this.array[index];
        }
        this.array = newArray;
        this.start = 0;
        this.end = this.size;
        this.capacity = newCapacity;
    }

    @Override
    public Iterator<T> iterator() {
        return new DequeIterator();
    }

  private class DequeIterator implements Iterator<T> {
    private int index;
  
    public DequeIterator() {
      this.index = start;
    }
  
    @Override
    public boolean hasNext() {
      return this.index < size;
    }
  
    @Override
    public T next() {
      if (!hasNext()) {
        throw new NoSuchElementException("Deque is empty");
      }
  
      T value = array[this.index];
      this.index = (this.index + 1) % capacity;
      return value;
    }
  
}
  

  public static void main(String[] args) {
      Deque<Integer> dq = new Deque<Integer>(10);
      for (int i = 0; i < 15; i++) {
          dq.pushFront(i);
      }
      StdOut.println(Arrays.toString(dq.peekAll()));

      for (int i = 0; i < 14 + 1; i++) {
          StdOut.println(dq.popBack());
      }
  }


public T[]  peekAll() {
    return array;
}
}
