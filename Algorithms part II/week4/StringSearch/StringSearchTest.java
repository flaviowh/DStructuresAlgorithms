package week4.StringSearch;

import edu.princeton.cs.algs4.In;


public class StringSearchTest {
    public static void main(String[] args) {
        String text = new In("Algorithms part II\\week4\\StringSearch\\sample.txt").readAll();
        String ptrn = "never-ending";
        int res;


        // Brute Force algorithm
        long startTime = System.nanoTime();
        res = BruteForceSearch.search2(ptrn, text);
        long endTime = System.nanoTime();
        long time = (endTime - startTime) / 1000000;
        System.out.println("Brute Force algorithm: result: " + res + " time: " + time + " ms");

        // Rabin Karp algorithm
        startTime = System.nanoTime();
        RabinKarp rk = new RabinKarp(ptrn);
        res = rk.search(text);
        endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000;
        System.out.println("Rabin Karp algorithm: result: " + res + " time: " + time + " ms");

        // KMP algorithm
        startTime = System.nanoTime();
        KMP kmp = new KMP(ptrn);
        res = kmp.search(text);
        endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000;
        System.out.println("KMP algorithm: result: " + res + " time: " + time + " ms");

        // Boyer Moore algorithm
        startTime = System.nanoTime();
        BM bm = new BM(ptrn);
        res = bm.search(text);
        endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000;
        System.out.println("Boyer Moore algorithm: result: " + res + " time: " + time + " ms");

    }
}
