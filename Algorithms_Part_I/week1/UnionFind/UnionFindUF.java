package Algorithms_Part_I.week1.UnionFind;
// almost O(n)

public class UnionFindUF {
    public int[] id;
    private int[] rootCount;
    public int[] largestCount;
    // private int size;

    public UnionFindUF(int N) {
        // size = N;
        id = new int[N];
        largestCount = new int[N];
        rootCount = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            largestCount[i] = 0;
        }
    }

    private int root(int i) {
        while (i != id[i]) {
            id[i] = id[id[i]]; // path compression
            i = id[i];
        }
        return i;
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    public int findLargestNeighbor(int i) {
        int iroot = root(i);
        return largestCount[iroot];
    }

    public void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        int newLarge = p > q ? p : q;
        if (i == j)
            return;
        if (rootCount[i] < rootCount[j]) {
            id[i] = j;
            largestCount[j] = newLarge > largestCount[j] ? newLarge : largestCount[j];
            rootCount[j] += rootCount[i];
        } else {
            id[j] = i;
            largestCount[i] = newLarge > largestCount[i] ? newLarge : largestCount[i];
            rootCount[i] += rootCount[j];
        }
    }
}
