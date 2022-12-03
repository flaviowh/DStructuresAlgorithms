package week4.assignment;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[] arr;
    private int dimension;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        dimension = tiles.length;
        int size = tiles[0].length * dimension;
        arr = new int[size];
        int r = 0;
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j< tiles[0].length;j++){
                arr[r++] = tiles[i][j];
            }
        }
    }
                                           
    // string representation of this board
    public String toString(){
        String str = dimension + " \n";
        int index = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension ; j++){
                str = str + arr[index++] + " ";
            }
            str = str + "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension(){
        return dimension;
    }

    // number of tiles out of place
    public int hamming(){
        int d = 0;
        for(int i = 0; i < arr.length - 1; i++){
            if(arr[i] != i +1) d++;
        }
        return d;
    }

    private int position(int n){
        int pos = 0;
        for(int i =0; i< arr.length;i++){
            if(arr[i] == n){
                pos = i;
                break;
            }
        }

        return pos;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int d = 0;
        for (int i = 1; i < (dimension * dimension) ; i++) {
            int pos = position(i);
            int realRow = pos / dimension;
            int realCol = pos % dimension;
            int goalRow = (i - 1) / dimension;
            int goalCol = (i - 1) % dimension;
            if (!(realRow == goalRow && realCol == goalCol)) {
                d += Math.abs(goalRow - realRow) + Math.abs(goalCol - realCol);
            }
        }
        return d;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == null)
            return false;

        if (y == this) {
            return true;
        }

        if (!(y instanceof Board)) {
            return false;
        }
        
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        
        for(int i = 0; i < dimension * dimension; i ++){
            if(this.arr[i] != that.arr[i]) return false;
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        int[][] dirs = {{1,0}, {-1,0}, {0, 1}, {0, -1}};
        Stack<Board> neighborsStack = new Stack<>();
        int zeroIndex = 0;
        for(int i = 0; i < arr.length;i++){
            if(arr[i]== 0){
                zeroIndex = i;
                break;
            }
        }

        int row = zeroIndex / dimension;
        int col = zeroIndex % dimension;

        for (int i = 0; i < dirs.length; i++) {
            int newRow = row + dirs[i][0];
            int newCol = col + dirs[i][1];
            if (newRow < 0 || newRow >= dimension || newCol < 0 || newCol >= dimension) {
                continue;
            }
            int swap = arr[newRow * dimension + (newCol % dimension)];

            int[][] similar = new int[dimension][dimension];
            for (int r = 0; r < dimension; r++) {
                for (int j = 0; j < dimension; j++) {
                    if (r == newRow && j == newCol) {
                        similar[r][j] = 0;
                    } else if (r == row && j == col) {
                        similar[row][col] = swap;
                    } else {
                        similar[r][j] = arr[r * dimension + (j % dimension)] != 0 ? arr[r * dimension + (j % dimension)]
                                : r * dimension + (j % dimension) + 1;
                    }
                }
            }

            Board similarBoard = new Board(similar);
            neighborsStack.push(similarBoard);

        }
        return neighborsStack;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int first = 0;
        int second = 0;
        int[][] twin = new int[dimension][dimension];
        int k = 0;
        while(first == 0 && second == 0){
            if (arr[k] != 0 && arr[k+1] != 0) {
                first = k;
                second = k + 1;
            }
            k++;
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == first) {
                twin[i / dimension][i % dimension] = arr[second];
            } else if (i == second) {
                twin[i / dimension][i % dimension] = arr[first];
            } else {
                twin[i / dimension][i % dimension] = arr[i];
            }
        }
        return new Board(twin);

    }
            

        

    

    // unit testing (not graded)
    public static void main(String[] args){
        // int[][] tiles = {{1,0,3},{4,2,5},{7,8,6}};
        // Board b = new Board(tiles);
        // Board b2 = new Board(tiles);
        // StdOut.println(b.equals(b2));
        // int[][] dtiles = {{8,1,3},{4,0,2},{7,6,5}};
        // Board b3 = new Board(dtiles);
        // StdOut.println(b3.hamming());
        // Iterable<Board> boards = b.neighbors();
        // for(Board board: boards){
        //     StdOut.println(board);
        // }
        // Board b4 = b.twin();
        // StdOut.println(b4);
        
    }

}