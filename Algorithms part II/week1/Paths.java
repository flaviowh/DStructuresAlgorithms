package week1;


public interface  Paths {

    // is there a path from s to v?
    abstract boolean hasPathTo(int v);

    // path from s to v; null if no such path
    abstract Iterable<Integer> pathTo(int v);

}
