package Algorithms_Part_I.week4.assignment;


import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

//logic from https://github.com/AlexJoz/ 

public class Solver {
    private BNode node;
    private BNode twinNode;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<BNode> bQueue = new MinPQ<>();
        int moves = 0;
        node = new BNode(initial, 0);
        twinNode = new BNode(initial.twin(), 0);

        bQueue.insert(node);
        bQueue.insert(twinNode); // prevents looping forever?
        bQueue.delMin();

        while (!node.isGoal()) {

            Stack<Board> neighborsStack = new Stack<Board>();

            for (Board b : node.bNeighbors()) {
                neighborsStack.push(b);
            }

            moves = node.moves + 1;

            for (Board b : neighborsStack) {
                Board bPrevious = null;

                if (node.previous != null) {
                    bPrevious = node.previous.getBoard();
                }

                if (!b.equals(bPrevious)) {
                    BNode newNode = new BNode(b, moves);
                    newNode.previous = node;
                    bQueue.insert(newNode);
                }
            }


            node = bQueue.delMin();

        }

    }

    // // is the initial board solvable? (see below)
    public boolean isSolvable() {
        BNode first = node;

        while (first.previous != null) {
            first = first.previous;
        }

        return first.getBoard().equals(twinNode.getBoard()) ? false : true;
    }

    // // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return node.moves;
        }

        return -1;
    }

    // // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        BNode sequNode = node;

        if (isSolvable()) {
            Stack<Board> solutionStack = new Stack<Board>();

            solutionStack.push(sequNode.getBoard());
            while (sequNode.previous != null) {
                sequNode = sequNode.previous;
                solutionStack.push(sequNode.getBoard());
            }

            return solutionStack;

        }
        return null;
    }

    // // test client (see below)
    // public static void main(String[] args)

    private class BNode implements Comparable<BNode> {
        private Board iBoard;
        private int moves;
        private BNode previous;

        public BNode(Board iBoard, int moves) {
            this.iBoard = iBoard;
            this.moves = moves;
            previous = null;
        }

        public Board getBoard() {
            return iBoard;
        }

        public Iterable<Board> bNeighbors() {
            return iBoard.neighbors();
        }

        public boolean isGoal() {
            return iBoard.isGoal();
        }

        public int distance() {
            return moves + iBoard.manhattan();
        }

        @Override
        public int compareTo(BNode that) {
            return this.distance() - that.distance();
        }

        @Override
        public String toString() {
            return iBoard.toString();
        }
    }

}