import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private Item[] a;
  private int n;

  // construct an empty randomized queue
//  @SuppressWarnings("unchecked")
  public RandomizedQueue() {
    a = (Item[]) new Object[2];
    n = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return n == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return n;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("null item");
    }
    if (n == a.length) {
      resize(a.length * 2);
    }
    a[n++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    int r = StdRandom.uniformInt(n);
    Item item = a[r];
    a[r] = a[n - 1];
    a[n - 1] = null;
    n--;
    if (n > 0 && n == a.length / 4) {
      resize(a.length / 2);
    }
    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }
    int r = StdRandom.uniformInt(n);
    return a[r];
  }

  // return an independent iterator over items in random order
  @Override
  public Iterator<Item> iterator() {
    return new RandomIterator();
  }

  private void resize(int capacity) {
//    @SuppressWarnings("unchecked")
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < n; i++) {
      copy[i] = a[i];
    }
    a = copy;
  }

  private class RandomIterator implements Iterator<Item> {

    private final Item[] iterArray;
    private int i;

//    @SuppressWarnings("unchecked")
    public RandomIterator() {
      iterArray = (Item[]) new Object[n];
      for (int j = 0; j < n; j++) {
        iterArray[j] = a[j];
      }
      StdRandom.shuffle(iterArray);
      i = 0;
    }

    @Override
    public boolean hasNext() {
      return i < iterArray.length;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return iterArray[i++];
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> rq = new RandomizedQueue<>();
    for (int i = 1; i <= 10; i++) {
      rq.enqueue(i);
    }

    System.out.println("sample: " + rq.sample());
    System.out.println("dequeue: " + rq.dequeue());
    System.out.println("size after dequeue: " + rq.size());

    System.out.print("iterator 1: ");
    for (int x : rq) {
      System.out.print(x + " ");
    }
    System.out.println();

    System.out.print("iterator 2: ");
    for (int x : rq) {
      System.out.print(x + " ");
    }
    System.out.println();
  }
}
