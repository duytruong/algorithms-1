import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by duyrau on 4/11/17.
 */
public class BruteCollinearPoints {

    private List<LineSegment> segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || hasNullItem(points)) {
            throw new NullPointerException("Input is null or contains a null item");
        }
        if (hasRepeatedPoints(points)) {
            throw new IllegalArgumentException("Input has repeated points");
        }

        int len = points.length;
        segments = new ArrayList<>();
        Point[] sortedPoints = Arrays.copyOf(points, len);
        Arrays.sort(sortedPoints);

        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    for (int l = k + 1; l < len; l++) {
                        double ijSlope = sortedPoints[i].slopeTo(sortedPoints[j]);
                        double ikSlope = sortedPoints[i].slopeTo(sortedPoints[k]);
                        double ilSlope = sortedPoints[i].slopeTo(sortedPoints[l]);
                        if (ijSlope == ikSlope && ijSlope == ilSlope) {
                            segments.add(new LineSegment(sortedPoints[i], sortedPoints[l]));
                        }
                    }
                }
            }
        }
    }

    private boolean hasNullItem(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return true;
        }
        return false;
    }

    private boolean hasRepeatedPoints(Point[] points) {
        int len = points.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (points[i].compareTo(points[j]) == 0) return true;
            }
        }
        return false;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
