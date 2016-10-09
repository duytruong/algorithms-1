import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private Percolation mPercolation;

    private double[] thresholds;

    private int mTrials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("The input must be greater than 0");
        }
        thresholds = new double[trials];
        mTrials = trials;
        calcualculateThresholdRepeatedly(n, trials);
    }

    private double calculateThreshold(int sideLength) {
        int opened = 0;
        while (!mPercolation.percolates()) {
            int r = StdRandom.uniform(1, sideLength + 1);
            int c = StdRandom.uniform(1, sideLength + 1);
            if (!mPercolation.isOpen(r, c)) {
                mPercolation.open(r, c);
                ++opened;
            }
        }
        return (float) opened / (sideLength * sideLength);
    }

    private void calcualculateThresholdRepeatedly(int n, int trials) {
        for (int i = 0; i < trials; i++) {
            mPercolation = new Percolation(n);
            thresholds[i] = calculateThreshold(n);
            mPercolation = null;

        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }


    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(mTrials));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(mTrials));
    }

    public static void main(String[] args) {
        if (args.length == 2 && !"".equals(args[0]) && !"".equals(args[1])) {
            PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]));
            StdOut.println("mean = " + stats.mean());
            StdOut.println("stddev = " + stats.stddev());
            StdOut.println("95% confidence interval = " +
                    stats.confidenceLo() + ", " + stats.confidenceHi());
        }
    }
}
