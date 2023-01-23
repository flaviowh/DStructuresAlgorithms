package week5.DataCompression;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class RunLength {
    private final static int R = 256;
    private final static int lgR = 8;

    public static void expand() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            int run = BinaryStdIn.readInt(lgR);
            for (int i = 0; i < run; i++)
                BinaryStdOut.write(bit);
            bit = !bit;
        }
        BinaryStdOut.close();
    }

    public static void compress() {
        char cnt = 0;
        boolean b, old = false;
        while (!BinaryStdIn.isEmpty()) {
            b = BinaryStdIn.readBoolean();
            if (b != old) {
                BinaryStdOut.write(cnt);
                cnt = 0;
                old = !old;
            } else {
                if (cnt == 255) {
                    BinaryStdOut.write(cnt);
                    cnt = 0;
                    BinaryStdOut.write(cnt);
                }
            }
            cnt++;
        }
        BinaryStdOut.write(cnt);
        BinaryStdOut.close();
    }

}