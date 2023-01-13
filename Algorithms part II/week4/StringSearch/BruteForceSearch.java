package week4.StringSearch;

public class BruteForceSearch {

    public static int search(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++)
                if (txt.charAt(i + j) != pat.charAt(j))
                    break;
            if (j == M)
                return i;
        }
        return -1;
    }

    public static int search2(String pat, String txt) {
        //  i points to end of sequence of already-matched chars in text.
        //  j stores # of already-matched chars (end of sequence in pattern).
        int i, N = txt.length();
        int j, M = pat.length();

        for (i = 0, j = 0; i < N && j < M; i++) {
            if (txt.charAt(i) == pat.charAt(j))
                j++;
            else {
                i -= j;
                j = 0;
            }
        }
        if (j == M)
            return i - M;
        else
            return -1;
    }

}
