import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private static final double CONST = 1.96;
  private final double[] data;
  private final int edge;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.data = new double[trials];
    edge = n;
    for (int i = 0; i < trials; i++) {
      data[i] = trial();
    }
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(data);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(data);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - CONST * stddev() / Math.sqrt(data.length);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + CONST * stddev() / Math.sqrt(data.length);
  }

  private double trial() {
    Percolation percolation = new Percolation(edge);

    int total = edge * edge;
    int[] order = new int[total];
    for (int k = 0; k < total; k++) {
      order[k] = k + 1;  // 1..total
    }
    StdRandom.shuffle(order);

    for (int k = 0; k < total; k++) {
      int idx = order[k];               // 1..n*n
      int row = (idx - 1) / edge + 1;   // 1..n
      int col = (idx - 1) % edge + 1;   // 1..n
      percolation.open(row, col);
      if (percolation.percolates()) {
        return (double) percolation.numberOfOpenSites() / (edge * edge);
      }
    }
    return 1.0; // 理论上到不了这里
  }


  // test client (see below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percolationStats = new PercolationStats(n, trials);
    StdOut.println("mean = " + percolationStats.mean());
    StdOut.println("stddev = " + percolationStats.stddev());
    StdOut.println("95% confidence interval = " + "[" + percolationStats.confidenceLo() + ", "
        + percolationStats.confidenceHi() + "]");
  }
}
