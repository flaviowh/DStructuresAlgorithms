package week3.StringsProcessing;


import edu.princeton.cs.algs4.In;
import week3.NonComparingSorts.ThreeWayRadixSort;


// Finds the longest substring a very long string

public class LRS {
    public String longestRepeatingSubstring(String s) {
        int N = s.length();

        String[] suffixes = new String[N];
        for (int i = 0; i < N; i++)
            suffixes[i] = s.substring(i, N);

        //Arrays.sort(suffixes);  // TimSort
        ThreeWayRadixSort.sort(suffixes);


        String lrs = "";
        for (int i = 0; i < N - 1; i++) {
            int len = lcp(suffixes[i], suffixes[i + 1]).length();
            if (len > lrs.length())
                lrs = suffixes[i].substring(0, len);
        }
        return lrs;

    }


        // return the longest common prefix of s and t
        public static String lcp(String s, String t) {
            int n = Math.min(s.length(), t.length());
            for (int i = 0; i < n; i++) {
                if (s.charAt(i) != t.charAt(i))
                    return s.substring(0, i);
            }
            return s.substring(0, n);
        }
    
    public static void main(String[] args) {
        //String mobyDick = new In("https://www.gutenberg.org/files/2701/old/moby10b.txt").readAll();
        String smallString = new In("Algorithms part II\\week3\\StringsProcessing\\longString.txt").readString();
        //String amendments = new In("http://hrlibrary.umn.edu/education/all_amendments_usconst.htm").readAll();
        System.out.println(new LRS().longestRepeatingSubstring(smallString));
        
    }
}
