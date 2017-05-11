package my.duyrau;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by duyrau on 4/21/17.
 */
public class FastCollinearPoints {

    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        int len = points.length;
        segments = new ArrayList<>();
        Arrays.sort(points);
        Point[] sortedPoints;

        for (int i = 0; i < len; i++) {
            // sort points by natural order first.
            sortedPoints = Arrays.copyOf(points, len);

            // then sort by slope
            Arrays.sort(sortedPoints, points[i].slopeOrder());

            int cnt = 0; // counter when calculate all slope made by point at start and others.
            double previousSlope = 0.0;
            List<Point> collinearPoints = new ArrayList<>();

            // 0th point is the origin
            while (cnt < len) {
                double slope = points[i].slopeTo(sortedPoints[cnt]);
                if (cnt == 0 || slope != previousSlope) {
                    if (collinearPoints.size() >= 3) {
                        collinearPoints.add(points[i]);
                        Collections.sort(collinearPoints);

                        // avoid add duplicate segments
                        // only accept segment have origin point is the smallest natural order
                        if (points[i] == collinearPoints.get(0)) {
                            segments.add(new LineSegment(points[i],
                                    collinearPoints.get(collinearPoints.size() - 1)));
                        }
                    }
                    collinearPoints.clear();
                }
                collinearPoints.add(sortedPoints[cnt]);
                previousSlope = slope;
                cnt++;
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }


    public static void main(String[] args) {
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

        // print and draw the line slopes
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
