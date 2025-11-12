import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  public static void main(String[] args) {
    RandomizedQueue<String> queue = new RandomizedQueue<>();
    int num = Integer.parseInt(args[0]);
    for (int i = 1; i < args.length; i++) {
      queue.enqueue(args[i]);
    }
    for (int i = 0; i < num; i++) {
      StdOut.println(queue.dequeue());
    }
  }
}
