package test.my.duyrau;

import my.duyrau.Deque;
import org.testng.Assert;

/**
 * Created by duytruong on 2/20/2017.
 */
public class DequeStateTest {

    Deque<Integer> deque = null;

    @org.testng.annotations.BeforeClass
    public void setUp() throws Exception {
        this.deque = new Deque<>();
    }

    @org.testng.annotations.Test(priority = 0)
    public void testIsEmpty() throws Exception {
        Assert.assertEquals(deque.isEmpty(), true);
    }

    @org.testng.annotations.Test(priority = 1)
    public void testSize() throws Exception {
        Assert.assertEquals(deque.size(), 0);
    }

    @org.testng.annotations.Test(priority = 2)
    public void testAddFirst() throws Exception {
        deque.addFirst(3);
        deque.addFirst(5);
        Assert.assertEquals(deque.size(), 2);
    }

    @org.testng.annotations.Test(priority = 3)
    public void testRemoveFirst() throws Exception {
        deque.removeFirst();
        Assert.assertEquals(deque.size(), 1);
    }

}
