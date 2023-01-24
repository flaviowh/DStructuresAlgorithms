package week6;

import java.util.Arrays;

// from slides, doesn't work '-' !!

public class Simplex {
    private double[][] a; // simplex tableaux
    private int m, n; // M constraints, N variables

    public Simplex(double[][] A, double[] b, double[] c) {
        m = b.length;
        n = c.length;
        a = new double[m + 1][m + n + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = A[i][j];
        for (int j = n; j < m + n; j++)
            a[j - n][j] = 1.0;
        for (int j = 0; j < n; j++)
            a[m][j] = c[j];
        for (int i = 0; i < m; i++)
            a[i][m + n] = b[i];
    }

    private int bland() {
        for (int q = 0; q < m + n; q++)
            if (a[m][q] > 0)
                return q;
        return -1;
    }

    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < m; i++) {
            if (a[i][q] <= 0)
                continue;
            else if (p == -1)
                p = i;
            else if (a[i][m + n] / a[i][q] < a[p][m + n] / a[p][q])
                p = i;
        }
        return p;
    }

    public void pivot(int p, int q) {
        for (int i = 0; i <= m; i++)
            for (int j = 0; j <= m + n; j++)
                if (i != p && j != q)
                    a[i][j] -= a[p][j] * a[i][q] / a[p][q];
        for (int i = 0; i <= m; i++)
            if (i != p)
                a[i][q] = 0.0;
        for (int j = 0; j <= m + n; j++)
            if (j != q)
                a[p][j] /= a[p][q];
        a[p][q] = 1.0;
    }

    public void solve() {
        while (true) {
            int q = bland();
            if (q == -1)
                break;
            int p = minRatioRule(q);
            if (p == -1) {
                System.out.println("Unbounded solution");
                return;
            }
            pivot(p, q);
        }
        System.out.println("Optimal solution found");
        printSolution();
    }

    public void printSolution() {
        for(int i = 0; i < a.length; i++)
        System.out.println(Arrays.toString(a[i]));
        // for (int i = 0; i < m; i++)
        //     System.out.println("x" + (i + 1) + " = " + a[i][m + n]);
    }

    public static void main(String[] args) {
        // Brewer's problem
        double[] b = { -13.0, -23.0};
        double[] c = { -480.0, -160.0, -1190.0 };
        double[][] A = {
                { -5.0, -4.0, -35.0 },
                { -15.0, -4.0, -20.0 }
        };

        TwoPhaseSimplex twoPhaseSimplex = new TwoPhaseSimplex(A, b, c);
        twoPhaseSimplex.show();

        Simplex simplex = new Simplex(A, b, c);
        simplex.solve();
    
    }
}
