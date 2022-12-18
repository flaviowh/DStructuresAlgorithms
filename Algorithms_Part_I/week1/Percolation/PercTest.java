package Algorithms_Part_I.week1.Percolation;

public class PercTest {
    public static void main(String[] args) {
        int n = 10;
        //int t = 100;
        //PercolationStats stats = new PercolationStats(n, t);
        Percolation perc = new Percolation(n);
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(2, 2);
        boolean ans1 = perc.isFull(2, 3);
        int ans2;
        System.out.println(ans1);
    }
}
