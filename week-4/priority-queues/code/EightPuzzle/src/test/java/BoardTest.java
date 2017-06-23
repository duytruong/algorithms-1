import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by duyrau on 6/21/17.
 */
public class BoardTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void calculateHamming33Correctly() {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        assertEquals(5, board.hamming());
    }

    @Test
    public void calculateHamming22Correctly() {
        int[][] blocks = {{1, 2}, {3, 0}};
        Board board = new Board(blocks);
        assertEquals(0, board.hamming());
    }

    @Test
    public void calculateHamming33Equals0() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertEquals(0, board.hamming());
    }

    @Test
    public void dimensionReturn0() {
        thrown.expect(NullPointerException.class);
        Board board = new Board(null);
    }

    @Test
    public void dimensionNotReturn0() {
        int[][] blocks = {{1, 2}, {3, 0}};
        Board board = new Board(blocks);
        assertEquals(2, board.dimension());
    }

    @Test
    public void calculateManhattan33Correctly() {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        assertEquals(10, board.manhattan());
    }

    @Test
    public void isGoal() {
        int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);
        assertEquals(true, board.isGoal());
    }

    @Test
    public void isNotGoal() {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        assertEquals(false, board.isGoal());
    }

    @Test
    public void twin33Board() {
        int[][] blocks = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board board = new Board(blocks);
        Board twin = board.twin();
        assertEquals(1, twin.getBlocks()[0][0]);
        assertEquals(8, twin.getBlocks()[0][1]);
        assertEquals(3, twin.getBlocks()[0][2]);
        assertEquals(4, twin.getBlocks()[1][0]);
        assertEquals(0, twin.getBlocks()[1][1]);
        assertEquals(2, twin.getBlocks()[1][2]);
        assertEquals(7, twin.getBlocks()[2][0]);
        assertEquals(6, twin.getBlocks()[2][1]);
        assertEquals(5, twin.getBlocks()[2][2]);
    }

    @Test
    public void testBoardEqual1() {
        int[][] blocks = {{1, 2}, {3, 0}};
        Board board1 = new Board(blocks);
        Board board2 = new Board(blocks);
        assertEquals(true, board1.equals(board2));
    }

    @Test
    public void testBoardEqual2() {
        int[][] blocks = {{1, 2}, {3, 0}};
        Board board1 = new Board(blocks);
        Board board2 = board1;
        assertEquals(true, board1.equals(board2));
    }

    @Test
    public void testBoardNotEqual1() {
        int[][] blocks = {{1, 2}, {3, 0}};
        int[][] blocks2 = {{1, 2}, {0, 3}};
        Board board1 = new Board(blocks);
        Board board2 = new Board(blocks2);
        assertEquals(false, board1.equals(board2));
    }

    @Test
    public void testBoardNotEqual2() {
        int[][] blocks = {{1, 2}, {3, 0}};
        Board board1 = new Board(blocks);
        assertEquals(false, board1.equals(null));
    }
}
