
//package queue;

import java.util.*;

class Queue<T> {

  private T[] values;
  private int front, rear, size;

  Queue() {
    this.size = 100;
    this.front = this.rear = -1;
    this.values = (T[]) new Object[this.size];
  }

  public boolean isEmpty() {
    return this.front == -1;
  }

  public boolean isFull() {
    return (this.front == this.rear + 1) || (this.front == 0 && this.rear == this.size - 1);
  }

  public void enQueue(T num) {
    if (isFull()) {
      System.err.println("QUEUE OVERFLOW\n");
      System.exit(0);
    }
    if (this.front == -1) {
      this.front = 0;
    }
    this.rear = (this.rear + 1) % this.size;
    this.values[this.rear] = num;
  }

  public T deQueue() {
    if (isEmpty()) {
      System.err.println("QUEUE UNDERFLOW\n");
      System.exit(0);
    }
    T x = this.values[this.front];
    if (this.front == this.rear) {
      this.front = this.rear = -1;
    }
    else {
      this.front = (this.front + 1) % this.size;
    }
    return x;
  }

  public void display() {
    System.out.print("Queue:\t");
    for (int i = this.front; i <= this.rear; i = (i + 1) % this.size) {
      System.out.printf("%d\t", this.values[i]);
    }
    System.out.println();
  }
}


public class QueueTest {

  public static void main(String[] args) {
    Queue<Integer> q = new Queue<Integer>();
    q.enQueue(1);
    q.enQueue(2);
    q.enQueue(3);
    q.display();
    System.out.println("Deleted element: " + q.deQueue());
    q.display();
  }

}
