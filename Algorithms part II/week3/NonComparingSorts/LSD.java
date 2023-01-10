package week3.NonComparingSorts;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

/* LSD radix sort uses a counting sort-like ubroutine to sort strings based on 
 their characters, starting with the least-significant. It has a time 
 complexity of O(wn) and is efficient for small strings with a limited number 
 of different characters.*/

// Least Significant Digit Sorting 
public class LSD {

    public static String[] sort(String[] a, int W) {
        int R = 256;
        int N = a.length;
        String[] aux = new String[N];

        for (int d = W - 1; d >= 0; d--) {
            int[] count = new int[R + 1];

            for (int i = 0; i < N; i++)
                count[a[i].charAt(d) + 1]++;

            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            for (int i = 0; i < N; i++)
                aux[count[a[i].charAt(d)]++] = a[i];

            for (int i = 0; i < N; i++) // maybe just return aux?
                a[i] = aux[i];
        }
        
        return a;
    }

    public static void main(String[] args) {
        String[] arr = { "dodo", "bacu", "nunoa", "nuno", "letona", "buseta", "arbor" };
        // it loses accuracy when strings have different sizes:  nunoa < nuno
        arr = LSD.sort(arr, 4);
        System.out.println(Arrays.toString(arr));
    }
}