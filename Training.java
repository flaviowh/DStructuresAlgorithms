import java.util.Arrays;
import java.util.stream.IntStream;

public class Training {
    public static void main(String[] args) {
        int n = 2;
        int[][] matrix;
        matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = i * n + j;
            }
        }
        for (int[] arr : matrix) {
            System.out.println(Arrays.toString(arr));
        }
    }
}