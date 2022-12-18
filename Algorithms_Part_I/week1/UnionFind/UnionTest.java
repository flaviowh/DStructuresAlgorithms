package Algorithms_Part_I.week1.UnionFind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class UnionTest {
    // test the union classes
    
    static WeightedQuickUnionUF uf = new WeightedQuickUnionUF(10);
    static UnionFindUF uf2 = new UnionFindUF(25);


    static Percolation perc = new Percolation(5);

    public static void main(String[] args) {
        // uf2.union(2,1);
        // uf2.union(2, 6);
        // uf2.union(9, 6);
        // uf2.union(4, 7);
        // uf2.union(5, 3);


        
        perc.open(0, 2);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 4);
        perc.open(4, 4);
        
        //boolean ans2 = perc.isConnected(2, 7);
        int ans2 = perc.numberOfOpenSites();
        System.out.println("\nconnected: " + ans2);

        boolean ans = perc.percolates();
        System.out.println(ans? "\nsystem percolates\n" : "\nsystem doesn't percolate\n");
    }

}
