import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

    private static final boolean X_ALIGN = true;

    private static class Node {
        private Point2D p;
        private final RectHV rect;
        private Node left;
        private Node right;

        private Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    private boolean align = true; // true: X, false: Y

    private Node root;

    private int size;

    private void doDraw(Node x, boolean orientation) {
        if (x == null) return;
        if (orientation == X_ALIGN) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        if (x.left != null) {
            doDraw(x.left, !orientation);
        }

        if (x.right != null) {
            doDraw(x.right, !orientation);
        }

        // draw point last to be on top of line
        StdDraw.setPenColor(StdDraw.BLACK);
        x.p.draw();
    }

    private Node doInsert(Node rootNode, Point2D p, boolean align, double xmin, double ymin, double xmax, double ymax) {
        // insert root node at the first time
        if (rootNode == null) {
//            System.out.print(p.toString() + " - ");
//            System.out.format("%f, %f, %f, %f", xmin, ymin, xmax, ymax);
//            System.out.println();
            Node n = new Node(p, new RectHV(xmin, ymin, xmax, ymax));
            n.p = p;
            size++;
            return n;
        }

        // handle when insert duplicated points
        if (p.equals(rootNode.p)) return rootNode;

        if (align == X_ALIGN) { // x align
            align = !align;
            if (p.x() < rootNode.p.x()) {
                rootNode.left = doInsert(rootNode.left, p, align,
                        rootNode.rect.xmin(), rootNode.rect.ymin(),rootNode.p.x(), rootNode.rect.ymax());
            } else {
                rootNode.right = doInsert(rootNode.right, p, align,
                        rootNode.p.x(), rootNode.rect.ymin(), rootNode.rect.xmax(), rootNode.rect.ymax());
            }
        } else { // y align
            align = !align;
            if (p.y() < rootNode.p.y()) {
                rootNode.left = doInsert(rootNode.left, p, align,
                        rootNode.rect.xmin(), rootNode.rect.ymin(), rootNode.rect.xmax(), rootNode.p.y());
            } else {
                rootNode.right = doInsert(rootNode.right, p, align,
                        rootNode.rect.xmin(), rootNode.p.y(), rootNode.rect.xmax(), rootNode.rect.ymax());
            }
        }
        return rootNode;
    }

    private boolean doContains(Node rootNode, Point2D p, boolean align) {
        if (rootNode == null) { // leaf node is null, stop
            return false;
        }
        if (rootNode.p.equals(p)) {
            return true;
        }
        if (align == X_ALIGN) {
            align = !align;
            if (p.x() < rootNode.p.x()) {
                return doContains(rootNode.left, p, align);
            } else {
                return doContains(rootNode.right, p, align);
            }
        } else {
            align = !align;
            if (p.y() < rootNode.p.y()) {
                return doContains(rootNode.left, p, align);
            } else {
                return doContains(rootNode.right, p, align);
            }
        }
    }

    private void doRange(Node rootNode, RectHV rect, List<Point2D> list) {
        if (rootNode == null) {
            return;
        }
        if (!rect.intersects(rootNode.rect)) {
               return;
        }
        if (rect.contains(rootNode.p)) {
            list.add(rootNode.p);
        }
        doRange(rootNode.left, rect, list);
        doRange(rootNode.right, rect, list);
    }

    private Point2D doNearest(Node rootNode, Point2D p, double currentMinDistance) {
        if (rootNode == null) return null;

        // stop finding on this subtree
        if (rootNode.rect.distanceSquaredTo(p) >= currentMinDistance) return null;

        Point2D nearestPoint = null;
        // currentMinDistance is distance from query point to the rectangle corresponding to the node.
        double nearestDistance = currentMinDistance;
        double d;

        d = p.distanceSquaredTo(rootNode.p);
        if (d < nearestDistance) {
            nearestPoint = rootNode.p;
            nearestDistance = d;
        }

        Node left = rootNode.left;
        Node right = rootNode.right;

        if (left != null && right != null) {
            if (left.rect.distanceSquaredTo(p) > right.rect.distanceSquaredTo(p)) {
                left = rootNode.right;
                right = rootNode.left;
                // left is the node closer to point
            }
        }

        // start finding in subtrees
        Point2D firstNearestPoint = doNearest(left, p, nearestDistance);
        if (firstNearestPoint != null) {
            d = p.distanceSquaredTo(firstNearestPoint);
            if (d < nearestDistance) {
                nearestPoint = firstNearestPoint;
                nearestDistance = d;
            }
        }

        Point2D secondNearestPoint = doNearest(right, p, nearestDistance);
        if (secondNearestPoint != null) {
            d = p.distanceSquaredTo(secondNearestPoint);
            if (d < nearestDistance) {
                nearestPoint = secondNearestPoint;
                nearestDistance = d;
            }
        }

        return nearestPoint;
    }

    public KdTree() {}

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void draw() {
        doDraw(root, X_ALIGN);
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        root = doInsert(root, p, align, 0, 0, 1, 1);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return false;
        return doContains(root, p, X_ALIGN);
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> points = new ArrayList<>();
        if (rect == null) throw new IllegalArgumentException();
        doRange(root, rect, points);
        return points;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return doNearest(root, p, Double.POSITIVE_INFINITY);
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        System.out.println(kdTree.size());
        System.out.println(kdTree.isEmpty());
        kdTree.insert(new Point2D(0.7, 0.2));
        System.out.println(kdTree.size());
        System.out.println(kdTree.isEmpty());
        kdTree.insert(new Point2D(0.7, 0.2));
        System.out.println(kdTree.size());
        System.out.println(kdTree.isEmpty());
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        System.out.println(kdTree.size());
        kdTree.range(new RectHV(0.1, 0.1, 0.5, 0.6)).forEach(p -> System.out.print(p.toString()));
        System.out.println(kdTree.nearest(new Point2D(0.5, 0.6)).toString());
        kdTree.draw();
        StdDraw.show();
    }
}
