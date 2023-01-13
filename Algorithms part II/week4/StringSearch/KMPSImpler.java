package week4.StringSearch;

// ChatGPT simplified version
public class KMPSImpler {
    private int[] partial;
    private String pattern;

    public KMPSImpler(String pattern) {
        this.pattern = pattern;
        int m = pattern.length();
        partial = new int[m];
        int j = 0;
        for (int i = 1; i < m; i++) {
            while (j > 0 && pattern.charAt(j) != pattern.charAt(i)) {
                j = partial[j - 1];
            }
            if (pattern.charAt(j) == pattern.charAt(i)) {
                j++;
            }
            partial[i] = j;
        }
    }

    public int search(String text) {
        int n = text.length();
        int m = pattern.length();
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j > 0 && pattern.charAt(j) != text.charAt(i)) {
                j = partial[j - 1];
            }
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
            }
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }
}
