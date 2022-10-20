import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double CONFD_95 = 1.96;
    private final int trialNum; // number of trails
    private final double[] percentOp; // the percentage of open sites


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        trialNum = trials;
        percentOp = new double[trialNum];

        for (int i = 0; i < trialNum; i++) {
            Percolation newTrial = new Percolation(n);
            int sitesOp = 0;


            // Selects a random site uniformly and open site until percolation
            while (!newTrial.percolates()) {
                int row = StdRandom.uniformInt(0, n);
                int col = StdRandom.uniformInt(0, n);

                // opens a site and increments the number of open sites
                if (!newTrial.isOpen(row, col)) {
                    newTrial.open(row, col);
                    sitesOp++;

                }
            }
            double percThershold = (double) sitesOp / (n * n);
            percentOp[i] = percThershold;
        }


    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percentOp);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (trialNum == 1) return Double.NaN;
        return StdStats.stddev(percentOp);

    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (CONFD_95 * stddev()) / Math.sqrt(trialNum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (CONFD_95 * stddev()) / Math.sqrt(trialNum);
    }


    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        Stopwatch stopwatch = new Stopwatch();

        PercolationStats ps = new PercolationStats(n, trials);
        double espT = stopwatch.elapsedTime();


        System.out.println("mean()           = " + ps.mean());
        System.out.println("stddev()         = " + ps.stddev());
        System.out.println("confidenceLow()  = " + ps.confidenceLow());
        System.out.println("confidenceHigh() = " + ps.confidenceHigh());
        System.out.println("elasped time     = " + espT);

    }


}
