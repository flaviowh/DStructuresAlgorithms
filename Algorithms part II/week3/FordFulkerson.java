package week3;

import Algorithms_Part_I.week2.Stack;
import edu.princeton.cs.algs4.Queue;


public class FordFulkerson {
    private boolean[] marked; // true if s->v path in residual network
    private FlowEdge[] edgeTo; // last edge on s->v path
    private double value; // value of flow
    private Stack<Integer> fattestPath = new Stack<>(); // finding fattest path

    public FordFulkerson(FlowNetwork G, int s, int t) {
        value = 0.0;
        double biggestCapacy = 0;
        while (hasAugmentingPath(G, s, t)) {
            double bottleneck = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) // compute blottleneck capacity
                bottleneck = Math.min(bottleneck, edgeTo[v].residualCapacityTo(v));

            for (int v = t; v != s; v = edgeTo[v].other(v)) // augment flow
                edgeTo[v].addResidualFlowTo(v, bottleneck);

            value += bottleneck;

            if(bottleneck > biggestCapacy){  //finding highest cap. path from s to t
                Stack<Integer> widestPath = new Stack<>();
                biggestCapacy = bottleneck;
                for (int x = t; x != s; x = edgeTo[x].from())
                widestPath.push(x);
                widestPath.push(s);
                fattestPath = widestPath;
            }
        }
    }

    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        edgeTo = new FlowEdge[G.V()];
        marked = new boolean[G.V()];
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                if (e.residualCapacityTo(w) > 0 && !marked[w]) {
                    edgeTo[w] = e;
                    marked[w] = true;
                    queue.enqueue(w);
                }
            }
        }

        return marked[t];
    }

    public double value() {
        return value;
    }

    public int[] widestPath(){ // returning fattest path
        int i = 0;
        int[] path = new int[fattestPath.size()];
        while(!fattestPath.isEmpty()){
            path[i++] = fattestPath.pop();
        }
        return path;
    }

    public boolean minCut(int v) {
        return marked[v];
    }

}