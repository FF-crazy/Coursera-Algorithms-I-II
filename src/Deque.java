import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private final LinkNode<Item> sentinel;
  private int size;

  // construct an empty deque
  public Deque() {
    sentinel = new LinkNode<>(null);
    sentinel.next = sentinel;
    sentinel.prev = sentinel;
    size = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    LinkNode<Item> temp = new LinkNode<>(item);
    temp.prev = sentinel;
    temp.next = sentinel.next;
    sentinel.next.prev = temp;
    sentinel.next = temp;
    size++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    LinkNode<Item> temp = new LinkNode<>(item);
    temp.next = sentinel;
    temp.prev = sentinel.prev;
    sentinel.prev.next = temp;
    sentinel.prev = temp;
    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    LinkNode<Item> temp = sentinel.next;
    sentinel.next = temp.next;
    temp.next.prev = sentinel;
    Item res = temp.item;
    temp.prev = null;
    temp.next = null;
    temp.item = null;
    size--;
    return res;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    LinkNode<Item> temp = sentinel.prev;
    sentinel.prev = temp.prev;
    temp.prev.next = sentinel;
    Item res = temp.item;
    temp.prev = null;
    temp.next = null;
    temp.item = null;
    size--;
    return res;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new DequeIterator();

  }

  private static class LinkNode<Item> {

    public LinkNode<Item> prev;
    public LinkNode<Item> next;
    public Item item;

    public LinkNode(Item item) {
      this.item = item;
    }
  }

  private class DequeIterator implements Iterator<Item> {

    public LinkNode<Item> current;

    public DequeIterator() {
      current = sentinel.next;
    }

    public boolean hasNext() {
      return current != sentinel;
    }

    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item res = current.item;
      current = current.next;
      return res;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }


  // unit testing (required)
  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(1);
    deque.addLast(5);
    deque.addFirst(2);
    deque.addFirst(3);
    deque.addLast(100);
    deque.removeFirst();
    deque.removeLast();
    for (int i : deque) {
      StdOut.println(i);
    }

  }

}