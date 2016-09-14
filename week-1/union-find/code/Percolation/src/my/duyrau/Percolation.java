package my.duyrau;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF mWeightedQU;

    // stores state of a site - true: open; false: blocked
    private boolean[] mStates;

    // size of the grid
    private int mSize;

    private int mSideLength;

    private void validate(int p) {
        if (p <= 0 || p > mSideLength) {
            throw new IndexOutOfBoundsException("row (column) index out of bounds");
        }
    }

    private int convert2DTo1D(int row, int column) {
        return row * mSideLength + column;
    }

    private boolean isValidCoordinate(int row, int column) {
        return (row > 0 && row <= mSideLength && column > 0 && column <= mSideLength);
    }

    public Percolation(int n) {
        validate(n);
        mSize = n * n;
        mSideLength = n;
        // 2 slots for virtual top site and virtual bottom site
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

    public void open(int i, int j) {
        validate(i);
        int r = i - 1;
        validate(j);
        int c = j - 1;
        
        int currentIdx = convert2DTo1D(r, c);
        mStates[currentIdx] = true;

        // top
        if (isValidCoordinate(r - 1, c) && isOpen(r - 1, c)) {
            int adjacentIdx = convert2DTo1D(r - 1, c);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }
        // right
        if (isValidCoordinate(r, c + 1) && isOpen(r, c + 1)) {
            int adjacentIdx = convert2DTo1D(r, c + 1);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }
        // bottom
        if (isValidCoordinate(r + 1, c) && isOpen(r + 1, c)) {
            int adjacentIdx = convert2DTo1D(r + 1, c);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }
        // left
        if (isValidCoordinate(r, c - 1) && isOpen(r, c - 1)) {
            int adjacentIdx = convert2DTo1D(r, c - 1);
            mWeightedQU.union(adjacentIdx, currentIdx);
        }

    }

    // a full site is an open site
    public boolean isOpen(int i, int j) {
        validate(i);
        validate(j);
        int idx = convert2DTo1D(i, j);
        return mStates[idx];
    }

    public boolean isFull(int i, int j) {
        validate(i);
        int r = i - 1;
        validate(j);
        int c = j - 1;
        int currentIdx = convert2DTo1D(r, c);
        return mWeightedQU.connected(currentIdx, mSize);
    }

    public boolean percolates() {
        return mWeightedQU.connected(mSize, mSize + 1);

    }

    public static void main(String[] args) {
    }
}