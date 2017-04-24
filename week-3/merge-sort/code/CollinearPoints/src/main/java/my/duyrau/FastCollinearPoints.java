import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by duyrau on 4/21/17.
 */
public class FastCollinearPoints {

    private HashMap<Point, List<Double>> slopes;
    private List<LineSegment> segments;

    public FastCollinearPoints(Point[] points) {
        int len = points.length;
        slopes = new HashMap<>();
        segments = new ArrayList<>();
        Arrays.sort(points);
        Point[] sortedPoints;

        // below array is used to sort the points by slope with the origin point.
//        Point[] sortedPoints = Arrays.copyOf(points, len);

        for (int i = 0; i < len; i++) {
            // sort points by natural order first.
            sortedPoints = Arrays.copyOf(points, len);

            // sort by slope
            Arrays.sort(sortedPoints, points[i].slopeOrder());

            int start = 1;
            int cnt = 2; // counter when calculate all slope made by point at start and others.

            // 0th point is the origin
            double slope1 = sortedPoints[0].slopeTo(sortedPoints[start]);
            while (cnt < len) {

                double slope2 = sortedPoints[0].slopeTo(sortedPoints[cnt]);
                if ((slope1 != slope2 || cnt == len - 1)) {
                    if (slope1 == slope2) cnt++;

                    if (cnt >= start + 2 && !existedInSegment(sortedPoints[0], slope1)) {
                        addToSegment(sortedPoints[0], slope1);

                        // add all points in the list of adjacent points have the equal slopes with origin.
                        // to avoid add duplicate points
                        for (int k = start; k < cnt; k++) {
                            addToSegment(sortedPoints[k], slope1);
                        }
                        segments.add(new LineSegment(sortedPoints[0], sortedPoints[cnt - 1]));
                    }
                    start = cnt;
                    slope1 = slope2;

                }
                // if slope of 0 and 1 point equals slope of 0 and 2
                // check slope of 0,1 and 0,3, repeat until slopes are different, and then
                // we check if 3 (or more) adjacent points have equal slopes with origin.
                // Example
                // origin = 0, start = 1, cnt = 2
                // 0--1--2--3----4---5---6
                // 0-1 == 0-2 (by slope), increase cnt (cnt = 3)
                // 0-1 == 0-3
                cnt++;
            }
        }
    }

    private boolean existedInSegment(Point p, double slope) {
        if (slopes.containsKey(p)) {
            for (double s : slopes.get(p)) {
                if (s == slope) return true;
            }
        }
        return false;
    }

    private void addToSegment(Point p, double slope) {
        if (!slopes.containsKey(p)) {
            slopes.put(p, new ArrayList<>());
        }
        slopes.get(p).add(slope);
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
