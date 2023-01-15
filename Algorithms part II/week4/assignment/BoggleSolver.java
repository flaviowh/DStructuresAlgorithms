package week4.assignment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private WordTrie dict;

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        dict = new WordTrie(dictionary);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        return new DFS(board, dict).validWords();
    }

    private boolean isValidSquare(BoggleBoard board, int r, int c) {
        int maxRows = board.rows();
        int maxCols = board.cols();
        return r > 0 && r < maxRows && c > 0 && c < maxCols;
    }

    private Iterable<int[]> adjacents(BoggleBoard board, int r, int c) {

        Queue<int[]> adj = new Queue<>();
        int[][] positions = { { -1, 0 }, { -1, -1 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, 1 }, { 1, -1 }, { 1, 0 } };
        for (int[] pos : positions) {
            int newR = r + pos[0];
            int newC = c + pos[1];
            if (isValidSquare(board, newR, newC)) {
                adj.enqueue(new int[] { newR, newC });
            }
        }
        return adj;
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (isValidWord(word)) {
            int len = word.length();
            if (len == 3 || len == 4)
                return 1;
            else if (len == 5)
                return 2;
            else if (len == 6)
                return 3;
            else if (len == 7)
                return 5;
            else if (len >= 8)
                return 11;
        }
        return 0;
    }

    private boolean hasPrefix(String prefix) {
        return dict.containsPrefix(prefix);
    }

    private boolean isValidWord(String word) {
        return dict.contains(word);
    }

    private class DFS {
        private Set<String> validWords = new HashSet<>();
        private boolean[] marked;

        public DFS(BoggleBoard board, WordTrie dict) {
            marked = new boolean[board.rows() * board.cols()];
            for (int r = 0; r < board.rows(); r++) {
                for (int c = 0; c < board.cols(); c++) {
                    dfs(board, dict, r, c, new StringBuilder());
                }
            }
        }

        private void dfs(BoggleBoard board, WordTrie dict, int r, int c, StringBuilder path) {
            if (!isValidSquare(board, r, c))
                return;

            int index = r * board.cols() + c;
            if (marked[index])
                return;

            marked[index] = true;
            char ch = board.getLetter(r, c);
            path.append(ch);
            String currentWord = path.toString();
            if (currentWord.length() >= 3 && dict.get(currentWord) != null)
                validWords.add(currentWord);
            if (!dict.containsPrefix(currentWord)) {
                path.deleteCharAt(path.length() - 1);
                marked[index] = false;
                return;
            }
            for (int[] neighbor : adjacents(board, r, c)) {
                dfs(board, dict, neighbor[0], neighbor[1], path);
            }
            path.deleteCharAt(path.length() - 1);
            marked[index] = false;
        }

        public Iterable<String> validWords() {
            return validWords;
        }
    }

    public static void main(String[] args) {

        // Dictionary test
        String path = "Algorithms part II\\week4\\assignment\\dictionary-common.txt";
        BoggleSolver bs = new BoggleSolver(new In(path).readAllLines());
        BoggleBoard b = new BoggleBoard(4, 9);
        StdOut.println(b);

        // StdOut.println("adjacents of letter : "+ b.getLetter(2, 2));
        // for(int[] adj : bs.adjacents(b, 2, 2))
        // StdOut.println(Arrays.toString(adj));

        String query = "MAUL";
        StdOut.println(bs.scoreOf(query));
        // // StdOut.println(bs.hasPrefix("ZIP"));

        Iterable<String> words = bs.getAllValidWords(b);
        for (String word : words) {
            StdOut.print(word + " , ");
        }
    }

}
