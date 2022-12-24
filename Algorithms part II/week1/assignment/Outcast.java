package week1.assignment;

public class Outcast {
    private WordNet net;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.net = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];

        for (int i = 0; i < nouns.length; i++) {
            int d = 0;
            for (int j = i + 1; j != i; j = j + 1 % nouns.length) {
                d += net.distance(nouns[i], nouns[j]);
            }
            distances[i] = d;
        }
        int max = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] > max) {
                max = distances[i];
            }
        }
        return nouns[max];

    }

    // see test client below
    public static void main(String[] args) {
    }

}