package week1.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

// following https://github.com/AlexJoz/ inmplementation

public class WordNet {
    private HashMap<Integer, String> IdSyns;
    private HashMap<String, Bag<Integer>> nounsID;
    private Digraph D;
    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException("Invalid arguments");

        In synsetsInput = new In(synsets);
        In hypernymsInput = new In(hypernyms);
        IdSyns = new HashMap<>();
        nounsID = new HashMap<>();
        readSynsets(synsetsInput);
        readHypernyms(hypernymsInput);
        checkCycle(D);
        sap = new SAP(D);
    }

    private void readHypernyms(In hypernymsInput) {
        D = new Digraph(IdSyns.size());
        while (!hypernymsInput.isEmpty()) {
            String[] line = hypernymsInput.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int w = Integer.parseInt(line[i]);
                D.addEdge(v, w);
            }
        }
    }

    private void checkCycle(Digraph d) {
        DirectedCycle cycleChecker = new DirectedCycle(d);
        if (cycleChecker.hasCycle()) {
            throw new IllegalArgumentException("This Digraph contains cycles.");
        }
    }

    private void readSynsets(In synsets) {
        Bag<Integer> nounsBag;
        while (!synsets.isEmpty()) {
            String[] ins = synsets.readLine().split(",");
            int id = Integer.parseInt(ins[0]);
            String nouns = ins[1];
            IdSyns.put(id, nouns);
            for (String noun : nouns.split(" ")) {
                nounsBag = nounsID.get(noun);
                if (nounsBag != null) {
                    nounsBag.add(id);
                } else {
                    nounsBag = new Bag<Integer>();
                    nounsBag.add(id);
                    nounsID.put(noun, nounsBag);
                }
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsID.keySet();
    }

    // // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new NullPointerException("Invalid word argument.");

        return nounsID.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        return sap.length(nounsID.get(nounA), nounsID.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of
    // nounA and nounB in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        int ancestor = sap.ancestor(nounsID.get(nounA), nounsID.get(nounB));
        return IdSyns.get(ancestor);
    }

    public static String readFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
        }
        reader.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        return sb.toString();
        }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}