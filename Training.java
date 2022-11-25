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
    Set<int[]> walked = new HashSet<int[]>();
    int[] j = {3,2};
    int[] c = {3,1};
    int[] v = {3,2};
    walked.add(j);
    walked.add(c);
    StdOut.println(walked.contains(j));

    HashSet<List<Integer>> check = new HashSet<>();
    check.add(List.of(1,2));
    StdOut.println(check.contains(List.of(1,2)));
  }
}
