package week1;

 interface Graph {

    abstract void addEdge(int v, int w);

    abstract int V();

    abstract int E();

    abstract Iterable<Integer> adj(int v);

}
