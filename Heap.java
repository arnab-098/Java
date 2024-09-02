public class Heap {

  private int size, len;
  private int[] values;

  public Heap() {
    size = 100;
    len = 0;
    values = new int[size];
  }

  public String toString() {
    if (isEmpty()) {
      return "HEAP IS EMPTY";
    }
    String s = new String();
    for (int i=0; i<len; i++) {
      s += (values[i] + "\t");
    }
    return s;
  }

  private int parent(int pos) {
    return (pos - 1) / 2;
  }

  private int left(int pos) {
    return 2 * pos + 1;
  }

  private int right(int pos) {
    return 2 * pos + 2;
  }
  
  private void swap(int pos1, int pos2) {
    int temp = values[pos1];
    values[pos1] = values[pos2];
    values[pos2] = temp;
  }

  private boolean isLeaf(int pos) {
    if (pos > (len / 2) && pos <= len) {
      return true;
    }
    return false;
  }

  private void heapify(int pos) {
    int smallest = pos;
    if (left(pos) < len && values[left(pos)] < values[pos]) {
      smallest = left(pos);
    }
    if (right(pos) < len && values[right(pos)] < values[pos]) {
      smallest = right(pos);
    }
    if (smallest != pos) {
      swap(pos, smallest);
      heapify(smallest);
    }
  }

  private boolean isFull() {
    return len == size;
  }

  private boolean isEmpty() {
    return len == 0;
  }

  public void insert(int n) {
    if (isFull()) {
      System.out.println("HEAP OVERFLOW");
      return;
    }
    values[len] = n;
    int curr = len;
    while (values[curr] < values[parent(curr)]) {
      swap(curr, parent(curr));
      curr = parent(curr);
    }
    len++;
  }

  public Integer delete() {
    if (isEmpty()) {
      System.out.println("HEAP UNDERFLOW");
      return null;
    }
    int n = values[0];
    values[0] = values[--len];
    heapify(0);
    return n;
  }

}


class HeapTest {

  public static void main (String[] args) {
    Heap h = new Heap();
    System.out.println(h);
    h.insert(2);
    h.insert(1);
    h.insert(3);
    System.out.println(h);
    System.out.println(h.delete());
    System.out.println(h);
  }

}
