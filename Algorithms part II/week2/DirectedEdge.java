package week2;

public class DirectedEdge implements Comparable<DirectedEdge> {
    private final int v, w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    public int compareTo(DirectedEdge that) {
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
}