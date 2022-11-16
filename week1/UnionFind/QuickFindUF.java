package week1.UnionFind;

// Too expensive.  N^2  (INIALIZATION + UNION)

public class QuickFindUF {
    static int[] id;

    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++)
            if (id[i] == pid)
                id[i] = qid;
    }//[0,1,2,3,4,5,6,7,8,9]  vizualization help

    public int[] peek(){
        return id;
    }
}