package week4.StringSearch;
//  The choice of the algorithm depends on the specific use case and the 

import java.math.BigInteger;
import java.util.Random;

//  characteristics of the input. For example, if the pattern is very short 
//  and the alphabet is large, then Rabin-Karp might be the best choice.
//  If the pattern is longer and the alphabet is small, then Boyer-Moore 
//  may be a better choice. If the pattern is long but occurs infrequently, 
//  then KMP might be the best choice.  ~ CGPT

public class RabinKarp {
    private long patHash; // pattern hash value
    private int M; // pattern length
    private long Q; // modulus
    private int R; // radix
    private long RM; // R^(M-1) % Q

    public RabinKarp(String pat) {
        M = pat.length();
        R = 256;
        Q = longRandomPrime();
        RM = 1;
        for (int i = 1; i <= M - 1; i++)
            RM = (R * RM) % Q;
        patHash = hash(pat, M);
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private long hash(String key, int M) {
        long h = 0;
        for (int j = 0; j < M; j++)
            h = (R * h + key.charAt(j)) % Q;
        return h;
    }

    public int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        if (patHash == txtHash)
            return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash)
            // Monte Carlo version - not checking for spurious hash collision
                return i - M + 1;
        }
        return -1;
    }


}