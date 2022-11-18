

public class Training {

  public static void main(String[] args) {
    //return opened[row * N + (col % N)] == 1;
    
    int row = 10;
    int col = 1;
    int N = 10;
    int ans = row * N + (col % N);
    System.out.println(ans);
  }
}
