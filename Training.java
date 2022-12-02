import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Training {
  
  public static int binarySearch(int[] a, int key) {
    int lo = 0, hi = a.length - 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      if (key < a[mid])
        hi = mid - 1;
      else if (key > a[mid])
        lo = mid + 1;
      else
        return mid;
    }
    return -1;
  }

  public static String tString(int[]arr, int cols){
    String str = cols + " \n";
    int index = 0;
    for(int i = 0; i < cols; i++){
        for(int j = 0; j < cols ; j++){
            str = str + arr[index++] + " ";
        }
        str = str + "\n";
    }
    return str;
}

  public static void main(String[] args) {
    // return opened[row * N + (col % N)] == 1;
    int[] arr = {0, 1,2,3,4,5,6,7,8,9};
    int n = 3;
  


    StdOut.println(tString(arr, n));
  }
}
