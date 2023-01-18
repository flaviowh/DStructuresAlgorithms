package week4.StringSearch;

import java.util.Arrays;

public class DFA {

    public static int[][] DFAmatrix(String pat, int R) {
        int M = pat.length();
        int[][] dfa = new int[R][M];
        dfa[pat.charAt(0) % R][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c % R][j] = dfa[c % R][X];

            dfa[pat.charAt(j) % R][j] = j + 1;
            X = dfa[pat.charAt(j) % R][X];
        }
        return dfa;
    }

    public static int[] DFA1d(String pat, int R){
        int m = pat.length();
        int[] partial = new int[m];
        int j = 0;
        for (int i = 1; i < m; i++) {
            while (j > 0 && pat.charAt(j) != pat.charAt(i)) {
                j = partial[j - 1];
            }
            if (pat.charAt(j) == pat.charAt(i)) {
                j++;
            }
            partial[i] = j;
        }
        return partial;
    }
    
    public static void main(String[] args) {
        int R = 256;
        String pat = "aabbc";

        for (int[] arr : DFAmatrix(pat, 3)) {
            System.out.println(Arrays.toString(arr));
        }

        int[] partial = DFA1d(pat, R);
        System.out.println("\n"+ Arrays.toString(partial));
    }
}
