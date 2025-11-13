import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

  private final LineSegment[] segments;

  public BruteCollinearPoints(Point[] points) {
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

    for (int i = 0; i < n - 3; i++) {
      for (int j = i + 1; j < n - 2; j++) {
        for (int k = j + 1; k < n - 1; k++) {
          for (int l = k + 1; l < n; l++) {
            double s1 = copy[i].slopeTo(copy[j]);
            double s2 = copy[i].slopeTo(copy[k]);
            double s3 = copy[i].slopeTo(copy[l]);

            if (s1 == s2 && s1 == s3) {
              list.add(new LineSegment(copy[i], copy[l]));
            }
          }
        }
      }
    }

    segments = list.toArray(new LineSegment[0]);
  }

  public int numberOfSegments() {
    return segments.length;
  }

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
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}


