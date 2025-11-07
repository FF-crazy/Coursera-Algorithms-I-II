public class Percolation {

  private final int n;
  private final int virtualTop;
  private final int virtualBottom;

  private final int[] parent;
  private final int[] size;
  private final boolean[] open;

  private int openCount = 0;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("n must be > 0");
    }
    this.n = n;
    this.virtualTop = 0;
    this.virtualBottom = n * n + 1;

    int total = n * n + 2;
    parent = new int[total];
    size = new int[total];
    open = new boolean[total];

    for (int i = 0; i < total; i++) {
      parent[i] = i;
      size[i] = 1;
    }
    // 虚点是否“open”不影响逻辑，可不设
  }

  // 转换 (row, col) -> 1..n*n
  private int index(int row, int col) {
    if (row < 1 || row > n || col < 1 || col > n) {
      throw new IllegalArgumentException("row/col out of range");
    }
    return (row - 1) * n + col;
  }

  // path halving
  private int root(int x) {
    while (x != parent[x]) {
      parent[x] = parent[parent[x]];
      x = parent[x];
    }
    return x;
  }

  private boolean connected(int a, int b) {
    return root(a) == root(b);
  }

  private void union(int a, int b) {
    int ra = root(a), rb = root(b);
    if (ra == rb) {
      return;
    }
    if (size[ra] < size[rb]) {
      parent[ra] = rb;
      size[rb] += size[ra];
    } else {
      parent[rb] = ra;
      size[ra] += size[rb];
    }
  }

  // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    int p = index(row, col);
    if (open[p]) {
      return;           // 仅第一次打开时计数
    }
    open[p] = true;
    openCount++;

    // 顶/底行与虚点联通
    if (row == 1) {
      union(p, virtualTop);
    }
    if (row == n) {
      union(p, virtualBottom);
    }

    if (row > 1 && isOpen(row - 1, col)) {
      union(p, index(row - 1, col)); // up
    }
    if (row < n && isOpen(row + 1, col)) {
      union(p, index(row + 1, col)); // down
    }
    if (col > 1 && isOpen(row, col - 1)) {
      union(p, index(row, col - 1)); // left
    }
    if (col < n && isOpen(row, col + 1)) {
      union(p, index(row, col + 1)); // right
    }
  }

  // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    return open[index(row, col)];
  }

  // is the site (row, col) full? (连通到顶虚点)
  public boolean isFull(int row, int col) {
    return connected(virtualTop, index(row, col));
  }

  // returns the number of open sites
  public int numberOfOpenSites() {
    return openCount;
  }

  // does the system percolate?
  public boolean percolates() {
    return connected(virtualTop, virtualBottom);
  }

  public static void main(String[] args) {
  }
}
