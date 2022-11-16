package week1.UnionFind;

import java.util.Arrays;

public class Union {
    // test the union classes
    
    static UnionFindUF uf = new UnionFindUF(10);

    public static void main(String[] args) {
        uf.union(1,2);
        uf.union(2, 6);
        uf.union(9, 6);
        uf.union(4, 7);
        uf.union(5, 3);
   

        int ans = uf.findLargestNeighbor(2);
        int[]arr= uf.id;
        System.out.println(Arrays.toString(arr));
        System.out.println(ans);
    }

}
