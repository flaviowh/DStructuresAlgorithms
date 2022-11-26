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

  public static void main(String[] args) {
    // return opened[row * N + (col % N)] == 1;
    int[] arr = {2,3,4,5,1,23,2};

    StdOut.println(Arrays.toString(Arrays.copyOfRange(arr, 0, 3)));
  }
}
