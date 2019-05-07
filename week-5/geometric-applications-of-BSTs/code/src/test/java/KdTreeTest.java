import com.sun.tools.javac.util.ArrayUtils;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

@RunWith(JUnit4.class)
public class KdTreeTest {

    private KdTree kdTree;

    @Before
    public void setUp() {
        kdTree = new KdTree();
    }

    @Test
    public void testSizeOfEmptyTreeMustEqual0() {
        assertEquals(0, kdTree.size());
    }

    @Test
    public void testSizeOfNonEmptyTreeMustNotEqual0() {
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        assertEquals(5, kdTree.size());
    }

    @Test
    public void testEmptyTreeMustNotContainAPoint() {
        boolean isContain = kdTree.contains(new Point2D(0.7, 0.2));
        assertEquals(false, isContain);
    }

    @Test
    public void testNonEmptyTreeContainsAPoint() {
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        boolean isContain = kdTree.contains(new Point2D(0.5, 0.4));
        assertEquals(true, isContain);
    }

    @Test
    public void testNonEmptyTreeDoesntContainAPoint() {
        kdTree.insert(new Point2D(0.7, 0.2));
        kdTree.insert(new Point2D(0.5, 0.4));
        kdTree.insert(new Point2D(0.2, 0.3));
        kdTree.insert(new Point2D(0.4, 0.7));
        kdTree.insert(new Point2D(0.9, 0.6));
        boolean isContain = kdTree.contains(new Point2D(0.5, 0.5));
        assertEquals(false, isContain);
    }

    @Test
    public void testRangeSearchWithEmptyTree() {
        RectHV rectHV = new RectHV(0.25, 0.25, 0.75, 0.75);
        Iterable<Point2D> points = kdTree.range(rectHV);
        int size = 0;
        for (Point2D p : points) {
            ++size;
        }
        assertEquals(0, size);

    }
}
