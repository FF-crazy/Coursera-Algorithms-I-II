import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

  private final LineSegment[] segments;

  // finds all line segments containing 4 or more points
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }

    int n = points.length;
    Point[] copy = new Point[n];

    for (int i = 0; i < n; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
      copy[i] = points[i];
    }

    // 排序并检查重复点
    Arrays.sort(copy);
    for (int i = 1; i < n; i++) {
      if (copy[i].compareTo(copy[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }

    List<LineSegment> list = new ArrayList<>();

    // 对每个点作为 origin
    for (int i = 0; i < n; i++) {
      Point origin = copy[i];

      Point[] sortedBySlope = Arrays.copyOf(copy, n);
      Arrays.sort(sortedBySlope, origin.slopeOrder());

      int j = 1;

      while (j < n) {
        int start = j;
        double slope = origin.slopeTo(sortedBySlope[j]);

        // 找到与 origin 斜率相同的一段 [start..end]
        while (j + 1 < n && Double.compare(origin.slopeTo(sortedBySlope[j + 1]), slope) == 0) {
          j++;
        }
        int end = j;

        int count = end - start + 1;

        if (count >= 3) {
          Point min = origin;
          Point max = origin;
          for (int k = start; k <= end; k++) {
            if (sortedBySlope[k].compareTo(min) < 0) {
              min = sortedBySlope[k];
            }
            if (sortedBySlope[k].compareTo(max) > 0) {
              max = sortedBySlope[k];
            }
          }

          // 只在 origin 是整条线段中的最小点时加入，避免重复
          if (origin.compareTo(min) == 0) {
            list.add(new LineSegment(min, max));
          }
        }

        j = end + 1;
      }
    }

    segments = list.toArray(new LineSegment[0]);
  }

  // the number of line segments
  public int numberOfSegments() {
    return segments.length;
  }

  // the line segments
  public LineSegment[] segments() {
    return segments.clone();
  }

  public static void main(String[] args) {

    // read the n points from a file
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

    // print and draw the line segments
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}