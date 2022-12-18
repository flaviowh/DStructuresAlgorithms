package Algorithms_Part_I.week2;
import java.util.Arrays;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Testing {
    public static void main(String[] args) {
        IntStack stack = new IntStack(100);
        Iterator lista = stack.iterator();
        // for (int i = 0; i < 100; i++) {
        //     int num = StdRandom.uniformInt(0, 200);
        //     stack.push(num);
        // }
        int[] arr = {2,43,12,40,50,100,20,};

        for(int i: arr){
            stack.push(i);
        }

        int[] rand = StdRandom.permutation(10);
        StdOut.println(Arrays.toString(rand));
    }

}
