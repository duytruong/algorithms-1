/**
 * Created by duyrau on 6/4/17.
 */
public class Board {

    private int[][] blocks;

    public Board(int[][] blocks) {
        if (blocks == null) {
            throw new NullPointerException();
        }
//        if (blocks.length != blocks[0].length) {
//            throw new IllegalArgumentException("Parameter must be n-by-n array");
//        }
        this.blocks = blocks;
    }

    private void swap(int[][] arr, int i, int j, int x, int y) {
        int temp = arr[i][j];
        arr[i][j] = arr[x][y];
        arr[x][y] = temp;
    }

    public int dimension() {
        return this.blocks != null ? this.blocks.length : 0;
    }

    public int hamming() {
        // i * cols + j;
        if (this.blocks == null) {
            throw new NullPointerException();
        }
        int outOfPlace = 0;
        int rows = this.blocks.length;
        int cols = this.blocks[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (this.blocks[i][j] != (i * cols + j + 1)) { outOfPlace++; }
                // last item in the goal board is blank, so do not treat it as a block.
                if (i == rows - 1 && j == cols - 1) { outOfPlace--; }
            }
        }
        return outOfPlace;
    }

    public int manhattan() {
        int distance = 0;
        int dimen = dimension();
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                int value = this.blocks[i][j];
                if (value != 0) {
                    int expectedRow = (value - 1) / dimen;
                    int expectedCol = value - (expectedRow * dimen) - 1;
                    distance += Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
                }
            }
        }
        return distance;
    }

    public boolean isGoal() {
        if (this.blocks == null) {
            throw new NullPointerException();
        }
        int dimen = dimension();
        for (int i = 0; i < dimen; i++) {
            // ignore last block - the block with index dimen^2 - 1
            for (int j = 0; j < dimen - 1; j++) {
                if (this.blocks[i][j] != (i * dimen + j + 1)) { return false; }
            }
        }
        return true;
    }

    public Board twin() {
        int dimen = dimension();
        int[][] newBlocks = new int[dimen][dimen];
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                newBlocks[i][j] = this.blocks[i][j];
            }
        }

        // swap 2 blocks on a row
        if (newBlocks[0][0] != 0 && newBlocks[0][1] != 0) {
            // if blank block is not on the first two blocks of row 0, swap them.
            swap(newBlocks, 0, 0, 0, 1);
        } else {
            // otherwise, swap first two blocks of row 1.
            swap(newBlocks, 1, 0, 1, 1);
        }
        return new Board(newBlocks);
    }

    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        int dimen = this.dimension();
        if (dimen != that.dimension()) return false;
        for (int i = 0; i < dimen; i++) {
            for (int j = 0; j < dimen; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        return null;
    }

    public String toString() {
        return null;
    }

    // used for testing
    public int[][] getBlocks() {
        return this.blocks;
    }
}
