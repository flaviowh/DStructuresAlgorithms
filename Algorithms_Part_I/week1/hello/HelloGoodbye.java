package Algorithms_Part_I.week1.hello;

import edu.princeton.cs.algs4.StdOut;


public class HelloGoodbye {

    public static void main(String[] args) {
        if(args.length > 1){
        String name1 = args[0];
        String name2 = args[1];
        StdOut.println("Hello" + " " + name1 + " and " + name2 + ".");
        StdOut.println("Goodbye" + " " + name2 + " and " + name1 + ".");
    }}

}
