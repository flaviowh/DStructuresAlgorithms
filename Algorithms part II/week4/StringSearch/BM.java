package week4.StringSearch;

//Boyer-Moore 

// Boyer-Moore algorithm is generally considered more efficient for longer 
// patterns and when there are many occurrences of the pattern in the text.
// While KMP algorithm is considered more efficient when the pattern is 
// shorter and occurs less frequently in the text. ~ CGPT

public class BM {
    private final String pat;
    private int R = 256;
    private int[] right;

    public BM(String pat) {
        this.pat = pat;
        int M = pat.length();
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1;
        for (int j = 0; j < M; j++)
            right[pat.charAt(j)] = j;
    }

    public int search(String txt) {
        int N = txt.length();
        int M = pat.length();
        int skip;
        for (int i = 0; i <= N - M; i += skip) {
            skip = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (pat.charAt(j) != txt.charAt(i + j)) {
                    skip = Math.max(1, j - right[txt.charAt(i + j)]);
                    break;
                }
            }
            if (skip == 0)
                return i;
        }
        return -1;
    }

}
