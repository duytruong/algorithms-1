import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private WeightedQuickUnionUF mWeightedQU;

    // stores state of a site - true: open; false: blocked
    private boolean[] mStates;

    // size of the grid
    private int mSize;

    private int mSideLength;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("The input must be greater than 0");
        }
        mSize = n * n;
        mSideLength = n;
        // 2 slots for virtual top site and virtual bottom site
        // the mSize-th site is the top, (mSize - 1)-th site is the bottom.
        mWeightedQU = new WeightedQuickUnionUF(mSize + 2);
        mStates = new boolean[mSize];
        int startIndexOfBottomRow = mSideLength * (mSideLength - 1);
        for (int i = 0; i < mSize; ++i) {
            mStates[i] = false;

            // connect virtual top site to all sites in top row
            if (i < mSideLength) {
                mWeightedQU.union(i, mSize);
            }

            // connect virtual bottom site to all sites in bottom row
            if (i >= startIndexOfBottomRow) {
                mWeightedQU.union(i, mSize + 1);
            }
        }
    }

    private void validate(int p) {
        if (p <= 0 || p > mSideLength) {
            throw new IndexOutOfBoundsException("row (column) index out of bounds");
        }
    }

    // 1-based
    private int convert2DTo1D(int row, int column) {
        return (row - 1) * mSideLength + (column - 1);
    }

    // 1-based
    private boolean isValidCoordinate(int row, int column) {
        return (row > 0 && row <= mSideLength
                && column > 0 && column <= mSideLength);
    }

    public void open(int i, int j) {
        validate(i);
        validate(j);

        int currentIdx = convert2DTo1D(i, j);
        mStates[currentIdx] = true;

        // top
        if (isValidCoordinate(i - 1, j) && isOpen(i - 1, j)) {
            int adjacentIdx = convert2DTo1D(i - 1, j);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }
        // right
        if (isValidCoordinate(i, j + 1) && isOpen(i, j + 1)) {
            int adjacentIdx = convert2DTo1D(i, j + 1);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }
        // bottom
        if (isValidCoordinate(i + 1, j) && isOpen(i + 1, j)) {
            int adjacentIdx = convert2DTo1D(i + 1, j);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }
        // left
        if (isValidCoordinate(i, j - 1) && isOpen(i, j - 1)) {
            int adjacentIdx = convert2DTo1D(i, j - 1);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }

    }

    public boolean isOpen(int i, int j) {
        validate(i);
        validate(j);
        int idx = convert2DTo1D(i, j);
        return mStates[idx];
    }

    public boolean isFull(int i, int j) {
        validate(i);
        validate(j);
        int currentIdx = convert2DTo1D(i, j);
        return mStates[currentIdx] && mWeightedQU.connected(currentIdx, mSize);
    }

    public boolean percolates() {
        boolean state = true;
        if (mSideLength == 1) {
            state = mStates[0];
        }
        return state && mWeightedQU.connected(mSize, mSize + 1);

    }
}