package week3.NonComparingSorts;

import java.util.Arrays;

// Counting sort is a sorting algorithm with a time complexity of O(n + k), 
// where n is the number of elements in the input array and k is the range of 
// the input values. It is most efficient for sorting small arrays of integers 
// with a known range and is not generally suitable for sorting floating-point 
// values or arrays with a large range of values or a large number of elements.

public class CountingSort {
    private int[] aux;
    private int[] arr;
    private final int range;
    private final int N;

    public CountingSort(int[] intArray, int range) {
        this.N = intArray.length;
        this.arr = intArray;
        this.aux = new int[N];
        this.range = range;

    }

    public int[] sort() {
        int[] count = new int[range + 1];

        for (int i = 0; i < N; i++) // count ocurrences
            count[arr[i] + 1]++;

        for (int r = 0; r < range; r++) // accumulate counts
            count[r + 1] += count[r];

        for (int i = 0; i < N; i++)
            aux[count[arr[i]]++] = arr[i]; // move items

        for (int i = 0; i < N; i++) // maybe just return aux?
            arr[i] = aux[i];

        return arr;
    }

    public static void main(String[] args) {
        int[] x = { 32, 12, 23, 12, 54, 1, 3 };
        CountingSort sorter = new CountingSort(x, 55);
        System.out.print(Arrays.toString(sorter.sort()));
    }

}