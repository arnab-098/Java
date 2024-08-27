
import java.util.*;

class Queue <T> {

	private int rear, front, size;
	private T[] values;

	<T> T[] Queue() {
		@SuppressWarnings("unchecked")
		this.rear = this.front = -1;
		this.size = 100;
		this.values = (T[]) new Object[this.size];
	}

	public boolean isEmpty() {
		return this.front == -1;
	}

	public boolean isFull() {
		return (this.rear == this.front - 1) || (this.front == 0 && this.rear == this.size - 1);
	}

	public void enQueue(T num) {
		if (isFull()) {
			System.err.println("QUEUE OVERFLOW");
			return;
		}
		if (isEmpty()) {
			this.front = 0;
		}
		this.rear = (this.rear + 1) % this.size;
		this.values[this.rear] = num;
	}

	public T deQueue() {
		if (isEmpty()) {
			System.err.println("QUEUE UNDERFLOW");
			return null;
		}
		T x = this.values[this.front];
		if (this.front == this.rear) {
			this.front = this.rear = -1;
		}
		this.front = (this.front + 1) % this.size;
		return x;
	}

	public void display() {
		if (isEmpty()) {
			System.err.println("QUEUE IS EMPTY");
			return;
		}
		System.out.print("Queue:\t");
		for (int i=this.front; i!=this.rear; i = (i + 1) % this.size) {
			System.out.print(this.values[i] + "\t");
		}
		System.out.println(this.values[this.rear] + "\t");
	}

}


public class New {

	public static void main (String args[]) {
		Queue<String> q = new Queue<String>();
		q.enQueue("a");
		q.enQueue("b");
		q.enQueue("c");
		q.display();
		System.out.println("Deleted element: " + q.deQueue());
		q.display();
	}

}
