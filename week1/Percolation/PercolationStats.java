package week1.Percolation;

import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] results;
    private int numTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0 || trials < 0) {
            throw new IllegalArgumentException("n and trials must be positive.");
        }

        results = new double[trials];
        numTrials = trials;

        for (int trialNum = 0; trialNum < trials; trialNum++) {
            Percolation percolation = new Percolation(n);
            while (true) {
                int next = StdRandom.uniformInt(0, n * n);
                int row = next / n + 1;
                int col = next % n + 1;
                percolation.open(row, col);
                if (percolation.percolates()) {
                    double percentOpen = percolation.numberOfOpenSites() / ((double) (n * n));
                    results[trialNum] = percentOpen;
                    // System.out.println("completed trial :" + trialNum + " percent open: " +
                    // percentOpen);
                    break;
                }

            }
        }
    }

    private double sum(double... values) {
        double result = 0.0;
        for (double value : values)
            result += value;
        return result;
    }

    // sample mean of percolation threshold
    public double mean() {
        return sum(results) / (double) numTrials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double _mean = mean();
        double[] squares = new double[numTrials];
        for (int i = 0; i < results.length; i++) {
            squares[i] = Math.pow(results[i] - _mean, 2.0);
        }
        double variance = sum(squares) / (double) (numTrials - 1);
        return Math.sqrt(variance);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double s = stddev();
        return mean() - (1.96 * s) / Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double s = stddev();
        return mean() + (1.96 * s) / Math.sqrt(numTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.valueOf(args[0]);
        int t = Integer.valueOf(args[1]);
        PercolationStats stats = new PercolationStats(n, t);
        double mean = stats.mean();
        double stdev = stats.stddev();
        double loConf = stats.confidenceLo();
        double hiConf = stats.confidenceHi();
        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + stdev);
        System.out.println("95% confidence interval = [" + loConf + ", " + hiConf + "]");
    }

}