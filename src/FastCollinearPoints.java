import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

  private LineSegment[] segments;

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

    Arrays.sort(copy);
    for (int i = 1; i < n; i++) {
      if (copy[i].compareTo(copy[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }

    List<LineSegment> list = new ArrayList<>();

  }

  // the number of line segments
  public int numberOfSegments() {
    return segments.length;
  }

  // the line segments
  public LineSegment[] segments() {
   return segments;
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