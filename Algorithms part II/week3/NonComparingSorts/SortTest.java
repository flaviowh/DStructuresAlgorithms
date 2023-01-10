package week3.NonComparingSorts;

import java.util.Arrays;
import java.util.Random;

public class SortTest {

    private static final int NUM_TESTS = 10;
    private static final int NUM_STRINGS = 100000;
    private static final int MIN_STRING_LENGTH = 2;
    private static final int MAX_STRING_LENGTH = 10;
    private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyz0123456789";

    public static void main(String[] args) {

        // Generate a large number of random strings to use as the input data
        String[] strings = new String[NUM_STRINGS];
        Random rnd = new Random();
        for (int i = 0; i < NUM_STRINGS; i++) {
            StringBuilder sb = new StringBuilder();
            int length = MIN_STRING_LENGTH + rnd.nextInt(MAX_STRING_LENGTH - MIN_STRING_LENGTH + 1);
            for (int j = 0; j < length; j++) {
                sb.append(CHAR_SET.charAt(rnd.nextInt(CHAR_SET.length())));
            }
            strings[i] = sb.toString();
        }

        // Test the performance of the Timsort algorithm
        long timsortTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            String[] stringsCopy = Arrays.copyOf(strings, NUM_STRINGS);
            long startTime = System.currentTimeMillis();
            Arrays.sort(stringsCopy); // uses Timsort
            long endTime = System.currentTimeMillis();
            timsortTime += endTime - startTime;
        }
        timsortTime /= NUM_TESTS;

        // Test the performance of the Timsort algorithm
        long MSDtime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            String[] stringsCopy = Arrays.copyOf(strings, NUM_STRINGS);
            long startTime = System.currentTimeMillis();
            MSD.sort(stringsCopy);
            long endTime = System.currentTimeMillis();
            MSDtime += endTime - startTime;
        }
        MSDtime /= NUM_TESTS;

        // Test the performance of the ThreeWayRadixSort algorithm
        long threeWayRadixSortTime = 0;
        for (int i = 0; i < NUM_TESTS; i++) {
            String[] stringsCopy = Arrays.copyOf(strings, NUM_STRINGS);
            long startTime = System.currentTimeMillis();
            ThreeWayRadixSort.sort(stringsCopy);
            long endTime = System.currentTimeMillis();
            threeWayRadixSortTime += endTime - startTime;
        }
        threeWayRadixSortTime /= NUM_TESTS;

        // Compare the average times for each algorithm
        System.out.println("Average time for ThreeWayRadixSort: " + threeWayRadixSortTime + "ms");
        System.out.println("Average time for Timsort: " + timsortTime + "ms");
        System.out.println("Average time for MSD: " + MSDtime + "ms");
    }
}
