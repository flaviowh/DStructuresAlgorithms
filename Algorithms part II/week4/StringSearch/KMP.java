package week4.StringSearch;

public class KMP {
    private final String pat;
    private int M;
    private int[][] dfa;
    private int R = 256;
    private final int NOT_FOUND = -1;

    public KMP(String pat) {
        this.pat = pat;
        M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];
            dfa[pat.charAt(j)][j] = j + 1;
            X = dfa[pat.charAt(j)][X];
        }
    }

    public int search(String text) {
        int i, j;
        for (i = 0, j = 0; i < text.length() && j < M; i++)
            j = dfa[text.charAt(i)][j];
        if (j == M)
            return i - M;
        else
            return NOT_FOUND;
    }

}
