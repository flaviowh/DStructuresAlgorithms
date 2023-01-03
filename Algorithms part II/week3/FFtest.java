package week3;

import java.util.Arrays;
import java.util.Map;

import edu.princeton.cs.algs4.StdOut;

public class FFtest {
    public static void main(String[] args) {
        // Network flow - max flow value
        FlowNetwork network = new FlowNetwork(6);
        network.addEdge(new FlowEdge(0, 1, 10));
        network.addEdge(new FlowEdge(0, 3, 10));
        network.addEdge(new FlowEdge(1, 2, 4));
        network.addEdge(new FlowEdge(1, 3, 2));
        network.addEdge(new FlowEdge(1, 4, 8));
        network.addEdge(new FlowEdge(2, 5, 10));
        network.addEdge(new FlowEdge(3, 4, 9));
        network.addEdge(new FlowEdge(4, 2, 6));
        network.addEdge(new FlowEdge(4, 5, 10));
        FordFulkerson ff = new FordFulkerson(network, 0, 5);
        StdOut.println(ff.minCut(0));
        StdOut.println(ff.value());

        // Bipartite network problem
        double capacity = 1;
        Map<Integer, String> names = Map.of(1, "Alice", 2, "Bob", 3, "Carol", 4, "Dave", 5, "Eliza", 6, "Adobe", 7,
                "Amazon", 8, "Facebook", 9, "Google", 10, "Yahoo");

        FlowNetwork bipartiteNetwork = new FlowNetwork(12);
        // link the source to each person
        bipartiteNetwork.addEdge(new FlowEdge(0, 1, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(0, 2, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(0, 3, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(0, 4, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(0, 5, capacity));
        // now each person to the company that offered them jobs
        bipartiteNetwork.addEdge(new FlowEdge(1, 7, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(1, 6, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(1, 9, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(2, 6, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(2, 7, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(3, 6, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(3, 8, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(3, 9, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(4, 7, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(4, 10, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(5, 7, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(5, 10, capacity));
        // link each company to the sink
        bipartiteNetwork.addEdge(new FlowEdge(6, 11, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(7, 11, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(8, 11, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(9, 11, capacity));
        bipartiteNetwork.addEdge(new FlowEdge(10, 11, capacity));
        FordFulkerson ff2 = new FordFulkerson(bipartiteNetwork, 0, 11);
        System.out.println("Bipartite problem solution:");
        for (FlowEdge edge : bipartiteNetwork.edges()) {
            if (edge.flow() == 1) {
                int v = edge.from();
                int w = edge.to();
                String name1 = names.get(v);
                String name2 = names.get(w);
                // Print the pairing solution ignoring the nulls
                if (name1 != null && name2 != null)
                    System.out.println(name1 + " matches with " + name2);
            }
        }

        // widest path algorithm answer: 0 > 2 > 3 > 4> 6> 7 > 8 > 9
        FlowNetwork network3 = new FlowNetwork(10);
        network3.addEdge(new FlowEdge(0, 1, 5));
        network3.addEdge(new FlowEdge(0, 2, 15));
        network3.addEdge(new FlowEdge(1, 3, 20));
        network3.addEdge(new FlowEdge(2, 3, 10));
        network3.addEdge(new FlowEdge(3, 4, 5));
        network3.addEdge(new FlowEdge(4, 5, 5));
        network3.addEdge(new FlowEdge(4, 6, 10));
        network3.addEdge(new FlowEdge(5, 6, 15));
        network3.addEdge(new FlowEdge(6, 7, 10));
        network3.addEdge(new FlowEdge(7, 8, 5));
        network3.addEdge(new FlowEdge(8, 9, 20));
        network3.addEdge(new FlowEdge(9, 5, 10));

        FordFulkerson ff3 = new FordFulkerson(network3, 0, 9);
        StdOut.println("widest path : " + Arrays.toString(ff3.widestPath()));

        // widest path algorithm (from figure)

        FlowNetwork stationsNetwork = new FlowNetwork(7);
        String[] stations = new String[] { "Maldon", "Feering", "Tiptree", "Clacton", "Blaxhall", "Hardwich",
                "Dunwich" };
        int[][] connections = new int[][] { { 0, 1, 11 }, { 0, 2, 8 }, { 0, 3, 40 }, { 1, 2, 3 }, { 1, 5, 46 },
                { 5, 2, 29 }, { 2, 4, 31 }, { 3, 5, 17 }, { 5, 4, 40 }, { 4, 6, 15 }, { 5, 6, 43 } };
        for (int[] conn : connections) {
            stationsNetwork.addEdge(new FlowEdge(conn[0], conn[1], conn[2]));
        }

        FordFulkerson ff4 = new FordFulkerson(stationsNetwork, 0, 6);
        for (int i : ff4.widestPath()) {
            StdOut.println(stations[i]);
        }
        // wrong

        FlowNetwork network4 = new FlowNetwork(8);
        int[][] conns = new int[][] { { 0, 5, 7 }, { 1, 0, 9 }, { 1, 6, 7 }, 
        { 2, 1, 8 }, { 2, 3, 6 }, { 2, 6, 5 },{ 3, 2, 5 }, { 3, 6, 6 }, { 3, 7, 8 },
        { 4, 0, 5 }, { 5, 4, 5 }, { 5, 1, 8 }, { 5, 6, 2 }, { 6, 5, 5 }, { 6, 2, 4 },
         { 6, 7, 3 }, { 7, 3, 5 } };
        for (int[] conn : conns) {
            network4.addEdge(new FlowEdge(conn[0], conn[1], conn[2]));
        }
        FordFulkerson ff5 = new FordFulkerson(network4, 0, 7);
       StdOut.println("widest path 2 : " + Arrays.toString(ff5.widestPath()));
    }
}
