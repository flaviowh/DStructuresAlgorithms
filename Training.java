
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

    int row = 10;
    int col = 1;
    int N = 10;
    int ans = row * N + (col % N);
    System.out.println(ans);
  }
}
