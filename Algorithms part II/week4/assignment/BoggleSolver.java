package week4.assignment;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;

// KINDA WORKS BUT DIDN'T PASS THE TESTS! 😢
// working solution by I. Bhanuka at https://github.com/twitu/Algorithms_Part_II

public class BoggleSolver {
    private WordTrie trie;
    private boolean[] visited;
    private Set<String> words = new HashSet<>();

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        trie = new WordTrie(dictionary);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        findAllWords(board);
        return words;
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (trie.contains(word)) {
            int len = word.contains("QU") ? word.length() - 1 : word.length();
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

    private void findAllWords(BoggleBoard board) {
        visited = new boolean[board.rows() * board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                Arrays.fill(visited, false);
                dfs(board, i, j, new StringBuilder());
            }
        }
    }

    private void dfs(BoggleBoard board, int i, int j, StringBuilder sb) {
        if (visited[i * board.cols() + j])
            return;

        char letter = board.getLetter(i, j);
        sb.append(letter == 'Q' ? "QU" : letter);
        visited[i * board.cols() + j] = true;

        if (trie.contains(sb.toString())) {
            String word = sb.toString();
            if (word.length() >= 3 && !words.contains(word)) {
                words.add(word);
            }
        }

        if (!trie.containsPrefix(sb.toString())) {
            visited[i * board.cols() + j] = false;
            sb.deleteCharAt(letter == 'Q' ? sb.length() - 2 : sb.length() - 1);
            return;
        }

        for (int r = i - 1; r <= i + 1; r++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (r >= 0 && r < board.rows() && col >= 0 && col < board.cols()) {
                    if (!visited[r * board.cols() + col])
                        dfs(board, r, col, sb);
                }
            }
        }
        visited[i * board.cols() + j] = false;
        sb.deleteCharAt(letter == 'Q' ? sb.length() - 2 : sb.length() - 1);
    }

    public static void main(String[] args) {
        String dictPath = "Algorithms part II\\week4\\assignment\\dictionary-twl06.txt";
        In in = new In(dictPath);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(4,4);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            System.out.println(word);
            score += solver.scoreOf(word);
        }
        System.out.println("Score = " + score);
        System.out.println(board);
    }


}
