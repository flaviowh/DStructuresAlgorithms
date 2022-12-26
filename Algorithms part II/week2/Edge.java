package week2;

import java.util.Objects;

public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        return vertex == v? w : v;
    }

    
    public int compareTo(Edge that) {
        if (this.weight < that.weight)
            return -1;
        else if (this.weight > that.weight)
            return +1;
        else
            return 0;
    }

    @Override
    public String toString(){
        return this.v + " - " + this.w + "  " + this.weight;
    }

    // Making the edge objects hashable to support 
    // set and returning for the method that returns all the edges
    // ~ F
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Edge that = (Edge) o;

        return this.v == that.v &&
                this.w == that.w && this.weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v, w, weight);
    }

    public double weight() {
        return this.weight;
    }

}