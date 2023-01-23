package week5.DataCompression;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import edu.princeton.cs.algs4.Alphabet;

import edu.princeton.cs.algs4.BinaryStdOut;

public class Genome {

    public static void compress(String s) throws IOException {
        Alphabet DNA = new Alphabet("ACTG");
        FileOutputStream fos = new FileOutputStream("compressed.bin");
        // String s = BinaryStdIn.readString();
        int N = s.length();
        BinaryStdOut.write(N);
        for (int i = 0; i < N; i++) { // Write two-bit code for char.
            int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, DNA.lgR());
            fos.write(d);
        }
        BinaryStdOut.close();
        fos.close();
    }

    public static void expand() throws IOException {
        Alphabet DNA = new Alphabet("ACTG");
        int w = DNA.lgR();
        FileInputStream fis = new FileInputStream("compressed.bin");
        int N = fis.read();
        for (int i = 0; i < N; i++) { // Read 2 bits; write char.
            int c = fis.read();
            BinaryStdOut.write(DNA.toChar(c));
        }
        fis.close();
        BinaryStdOut.close();
    }

    public static void main(String[] args) throws IOException {
        String in = "ATAGATGAGCGCAGCGCCATTGACAGTCAGTGACGATGGACCATTAGCGCAGCAAAGCGCAGCGCCATTGACAGTCAGTGACGATGGACCATTAGCGCAGGCGCAGCGCCATTGACAGTCAGTGACGATGGACCATTAGCGCAGTAAGCGCATGCAATGCGCGCTAGATGTGCTAGCATTAGCTAGATGTGCTAGCAT";
        compress(in);
        expand();
    }
}
