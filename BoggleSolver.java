
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
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

    private boolean isValidWord(String word) {
        return dict.contains(word);
    }

    class DFS {
        private BoggleBoard board;
        private WordTrie dict;
        private boolean[][] marked;
        private Set<String> validWords;

        public DFS(BoggleBoard board, WordTrie dict) {
            this.board = board;
            this.dict = dict;
            this.marked = new boolean[board.rows()][board.cols()];
            this.validWords = new HashSet<>();

            for (int i = 0; i < board.rows(); i++) {
                for (int j = 0; j < board.cols(); j++) {
                    dfs(i, j, new StringBuilder());
                }
            }
        }

        private void dfs(int row, int col, StringBuilder sb) {
            char c = board.getLetter(row, col);
            sb.append(c);
            String prefix = sb.toString();
            if (!dict.containsPrefix(prefix)) {
                sb.deleteCharAt(sb.length() - 1);
                return;
            }
            if (prefix.length() >= 3 && dict.contains(prefix)) {
                validWords.add(prefix);
            }
            marked[row][col] = true;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) {
                        continue;
                    }
                    int newRow = row + i;
                    int newCol = col + j;
                    if (newRow < 0 || newRow >= board.rows() || newCol < 0 || newCol >= board.cols()) {
                        continue;
                    }
                    if (!marked[newRow][newCol])
                        dfs(newRow, newCol, sb);
                }
            }
            marked[row][col] = false;
            sb.deleteCharAt(sb.length() - 1);
        }
        

        public Iterable<String> validWords() {
            return validWords;
        }
    }

    public static void main(String[] args) {
        String boardPath = "C:\\Users\\flavi\\Desktop\\boggle\\board-points100.txt";
        String dictPath = "C:\\Users\\flavi\\Desktop\\boggle\\dictionary-algs4.txt";
        In in = new In(dictPath);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(boardPath);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
