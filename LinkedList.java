
import java.util.*;


class Node <T> {

	private T value;
	private Node<T> next;

	@SuppressWarnings({"unchecked", "unsafe"})

	Node (T n) {
		this.value = n;
		this.next = null;
	}

	public void setNext(Node<T> p) {
		this.next = p;
	}

	public Node<T> getNext() {
		return this.next;
	}

	public void setValue(T n) {
		this.value = n;
	}

	public T getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return this.value + "\t";
	}

}


public class LinkedList <T> {

	private Node<T> head;

	@SuppressWarnings({"unchecked", "unsafe"})

	LinkedList () {
		this.head = null;
	}

	public void push(T n) {
		Node<T> p = new Node<T>(n);
		if (this.head == null) {
			this.head = p;
		} else {
			p.setNext(this.head);
			this.head = p;
		}
	}

	public T pop() {
		if (this.head == null) {
			return null;
		}
		Node<T> p = this.head;
		this.head = this.head.getNext();
		return p.getValue();
	}

	@Override
	public String toString() {
		if (this.head == null) {
			return "Empty list";
		}
		String result = new String();
		Node<T> curr = this.head;
		while (curr != null) {
			result += curr;
			curr = curr.getNext();
		}
		return result;
	}

}


class Main {

	public static void main (String[] args) {
		LinkedList<String> l = new LinkedList<String>();
		l.push("a");
		l.push("b");
		l.push("c");
		System.out.println(l);
		String s;
		if ((s = l.pop()) != null) {
			System.out.println("Deleted element: " + s);
		}
		System.out.println(l);
	}

}
