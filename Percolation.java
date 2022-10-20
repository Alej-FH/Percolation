import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int virtualTop; // the starting point
    private boolean[] openStatus; // the status of a site
    private final int size; // size of the grid.
    private final int virtBottom; // the bottom of the grid
    private int openSites; // number of opensites.
    private final WeightedQuickUnionUF quf; // connected opensites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n; // size of grid
        virtualTop = size * size; // virtual top
        virtBottom = size * size + 1; // virtual bottom
        quf = new WeightedQuickUnionUF(size * size + 2); // Union-Find sites
        openStatus = new boolean[size * size];
        openSites = 0; // number of open sites
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        illegalArgumentException(row, col);

        if (isOpen(row, col)) return;

        int p = indexConverter(row, col);
        openStatus[p] = true;

        ++openSites; // increments number of open sites

        // All open first row boxes unionizes with virtual top
        if (row == 0) {
            quf.union(p, virtualTop);
        }
        // Unions an open site with another open site above it
        if (row > 0 && isOpen(row - 1, col)) {
            quf.union(p, indexConverter(row - 1, col));
        }
        // Unions an open site with another one below it
        if (row < size - 1 && isOpen(row + 1, col)) {
            quf.union(p, indexConverter(row + 1, col));
        }
        // Unions an open site with another one to its left
        if (col > 0 && isOpen(row, col - 1)) {
            quf.union(p, indexConverter(row, col - 1));
        }
        // Unions an open site with another to the right.
        if (col < size - 1 && isOpen(row, col + 1)) {
            quf.union(p, indexConverter(row, col + 1));
        }
        // All open sites at last row unionizes with virtual bottom
        if (row == size - 1) {
            quf.union(p, virtBottom);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        illegalArgumentException(row, col);
        return openStatus[indexConverter(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        illegalArgumentException(row, col);
        return quf.find(virtualTop) == quf.find(indexConverter(row, col));

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        // Checks if the roots of the quick-union match
        return quf.find(virtualTop) == quf.find(virtBottom);
    }

    // Checks the conditions for boundaries
    private void illegalArgumentException(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IllegalArgumentException();
        }
    }

    // Declares an index to the 2D Coordinates of a n-by-n grid
    private int indexConverter(int row, int col) {

        return size * row + col;
    }

    // unit testing (required)
    public static void main(String[] args) {

        Percolation test = new Percolation(3);
        System.out.println("Initial Site Open? = " + test.isOpen(0, 1));


        test.open(0, 1);
        System.out.println("Is (0,1) open?     = " + test.isOpen(0, 1));

        test.open(1, 1);
        System.out.println("Conversion (1,1)   = " + test.indexConverter(1, 1));

        System.out.println("Is (1,1) open?     = " + test.isOpen(1, 1));
        System.out.println("Is this site open? = " + test.isFull(1, 1));

        System.out.println("Total Open Sites   = " + test.numberOfOpenSites());
        System.out.println("Percolates?        = " + test.percolates());


    }

}
