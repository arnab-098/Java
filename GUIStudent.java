import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Student {

	private String name;
	private int roll;

	Student() {
		this.name = "default";
		this.roll = 0;
	}

	Student(String name, int roll) {
		this.name = name;
		this.roll = roll;
	}

	public String getName() {
		return this.name;
	}

	public int getRoll() {
		return this.roll;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public String toString() {
		return ("Name: " + this.name + "\nRoll: " + this.roll);
	}

}


class CustomList<T> implements Iterable<T> {

	private T[] list;
	private int size, idx;

	@SuppressWarnings("unchecked")
	CustomList(int size) {
		this.size = size;
		this.idx = -1;
		this.list = (T[]) new Object[size];
	}

	boolean isFull() {
		return (this.idx == this.size - 1);
	}

	boolean isEmpty() {
		return (this.idx == -1);
	}

	public void add(T obj) throws CustomException {
		if (isFull()) {
			throw new CustomException("List is full");
		}
		this.list[++this.idx] = obj;
	}

	public T remove() throws CustomException {
		if (isEmpty()) {
			throw new CustomException("List is empty");
		}
		return this.list[this.idx--];
	}

	public void display() {
		if (isEmpty()) {
			System.out.println("No student records found");
			return;
		}
		for (int i=0; i<=this.idx; i++) {
			System.out.println(this.list[i]);
			if (i < this.idx) {
				System.out.println();
			}
		}
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> itr = new Iterator<T>() {

			private int currentIndex = -1;

			@Override
			public boolean hasNext() {
				return (this.currentIndex < idx);
			}

			@Override
			public T next() {
				return list[++this.currentIndex];
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};

		return itr;
	}
}


class CustomException extends Exception {

	String error;

	CustomException(String error) {
		super(error);
		this.error = error;
	}

	public String getMessage() {
		return this.error;
	}

}


class SearchStudent {

	public static Student search(String name, CustomList<Student> students) throws CustomException{
		Iterator<Student> itr = students.iterator();		
		while (itr.hasNext()) {
			Student s = itr.next();
			if (name.equals(s.getName())) {
				return s;
			}
		}
		throw new CustomException("Student not found");
	}

}


public class GUIStudent implements ActionListener {

	private JTextField nameField, rollField, searchField;
	private JLabel label, info, searchInfo;
	private static CustomList<Student> students;
	private int BUFFER_SIZE = 10;

	GUIStudent() {
		this.students = new CustomList<Student>(this.BUFFER_SIZE);
		this.createFrame();
	}

	private void createFrame() {
		JFrame frame = new JFrame("Student Details");
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				display();
				e.getWindow().dispose();
			}
		});

		this.label = new JLabel("Enter the student details");
		this.label.setBounds(75, 50, 200, 50);

		this.nameField = new JTextField(10);
		this.nameField.setBounds(50, 100, 200, 50);
		this.rollField = new JTextField(3);
		this.rollField.setBounds(50, 150, 200, 50);

		this.info = new JLabel("");
		this.info.setBounds(75, 250, 300, 50);

		JButton submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setBounds(100, 200, 100, 50);

		this.searchField = new JTextField(10);
		this.searchField.setBounds(500, 100, 200, 50);

		JButton search = new JButton("Search");
		search.addActionListener(this);
		search.setBounds(550, 170, 100, 50);

		this.searchInfo = new JLabel("");
		this.searchInfo.setBounds(525, 200, 200, 100);

		frame.add(this.label);
		frame.add(this.nameField);
		frame.add(this.rollField);
		frame.add(this.info);
		frame.add(submit);
		frame.add(this.searchField);
		frame.add(search);
		frame.add(this.searchInfo);

		frame.setVisible(true);
	}

	private void addStudent() {
		try {
			String name = this.nameField.getText();
			int roll = Integer.parseInt(this.rollField.getText());
			this.students.add(new Student(name, roll));
			this.info.setText("Stduent " + name + " has been added!");
		} catch (NumberFormatException e) {
			this.info.setText(e.getMessage());
		} catch (CustomException e) {
			this.info.setText(e.getMessage());
		}
		this.nameField.setText("");
		this.rollField.setText("");
	}

	private void searchStudent() {
		try {
			String studentName = this.searchField.getText();
			Student s = SearchStudent.search(studentName, this.students);
			String searchResult = new String("<html>Name: " + s.getName() + "<br>Roll: " + s.getRoll() + "</html>");
			this.searchInfo.setText(searchResult);
		} catch (CustomException e) {
			this.searchInfo.setText(e.getMessage());
		}
	}

	private static void display() {
		students.display();
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Submit")) {
			this.addStudent();
		} else if (ae.getActionCommand().equals("Search")) {
			this.searchStudent();
		}
	}

}


class Test {
	public static void main(String[] args) {
		new GUIStudent();
	}
}