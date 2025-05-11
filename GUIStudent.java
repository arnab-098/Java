import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class Student {

	private String name;
	private int roll, score;

	Student() {
		this.name = "default";
		this.roll = 0;
		this.score = 0;
	}

	Student(String name, int roll, int score) {
		this.name = name;
		this.roll = roll;
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public int getRoll() {
		return this.roll;
	}

	public int getScore() {
		return this.score;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoll(int roll) {
		this.roll = roll;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String toString() {
		return (
			"Name: " + 
			this.name + 
			"\nRoll: " + 
			this.roll +
			"\nScore: " +
			this.score
		);
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

	public void display() throws CustomException {
		if (isEmpty()) {
			throw new CustomException("No student records found");
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


class StudentScoreModifier {

	private int INCREMENT_VALUE = 10;
	private int DECREMENT_VALUE = 10;
	private CustomList<Student> students;

	StudentScoreModifier(CustomList<Student> students) {
		this.students = students;
	}

	public synchronized void incrementScore() {
		Iterator<Student> itr = this.students.iterator();
		while (itr.hasNext()) {
			Student s = itr.next();
			if (s.getScore() == 100) {
				continue;
			}else if (s.getScore() >= 90) {
				s.setScore(100);
				continue;
			}
			s.setScore(s.getScore() + this.INCREMENT_VALUE);
		}
	}

	public synchronized void decrementScore() {
		Iterator<Student> itr = this.students.iterator();
		while (itr.hasNext()) {
			Student s = itr.next();
			if (s.getScore() == 0) {
				continue;
			} else if (s.getScore() <= 10) {
				s.setScore(0);
				continue;
			}
			s.setScore(s.getScore() - this.DECREMENT_VALUE);
		}
	}

}


class CustomSort implements Comparator<Student> {

	@Override
	public int compare(Student a, Student b) {
		return (a.getScore() - b.getScore());
	}

}


class ThreadScoreIncrementer implements Runnable {

	private String name;
	private StudentScoreModifier scoreModifier;
	private Thread thrd;

	ThreadScoreIncrementer(String name, StudentScoreModifier scoreModifier) {
		this.name = name;
		this.scoreModifier = scoreModifier;
		this.thrd = new Thread(this, name);
		this.thrd.start();
	}

	@Override
	public void run() {
		this.scoreModifier.incrementScore();
	}

	public String getName() {
		return this.name;
	}

	public void join() {
		try {
			this.thrd.join();
		} catch (InterruptedException e) {
			System.out.println("Main thread interrupted");
		}
	}

}


class ThreadScoreDecrementer implements Runnable {

	private String name;
	private StudentScoreModifier scoreModifier;
	private Thread thrd;

	ThreadScoreDecrementer(String name, StudentScoreModifier scoreModifier) {
		this.name = name;
		this.scoreModifier = scoreModifier;
		this.thrd = new Thread(this, name);
		this.thrd.start();
	}

	@Override
	public void run() {
		this.scoreModifier.decrementScore();
	}

	public String getName() {
		return this.name;
	}

	public void join() {
		try {
			this.thrd.join();
		} catch (InterruptedException e) {
			System.out.println("Main thread interrupted");
		}
	}

}


public class GUIStudent implements ActionListener {

	private JFrame frame;
	private JTextField nameField, rollField, scoreField, searchField;
	private JLabel info, searchInfo, scoreInfo;
	private CustomList<Student> students;
	private int BUFFER_SIZE = 10;

	GUIStudent() {
		this.students = new CustomList<Student>(this.BUFFER_SIZE);
		this.createFrame();
	}

	private void createFrame() {
		this.frame = new JFrame("Student Details");
		this.frame.setSize(500, 500);
		this.frame.setLayout(null);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				display();
				e.getWindow().dispose();
			}
		});

		JLabel instruction = new JLabel("Enter the student details");
		instruction.setBounds(75, 50, 200, 50);

		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(20, 100, 100, 50);
		this.nameField = new JTextField(10);
		this.nameField.setBounds(75, 100, 200, 40);

		JLabel rollLabel = new JLabel("Roll");
		rollLabel.setBounds(20, 150, 100, 50);
		this.rollField = new JTextField(2);
		this.rollField.setBounds(75, 150, 200, 40);

		JLabel scoreLabel = new JLabel("Score");
		scoreLabel.setBounds(20, 200, 100, 50);
		this.scoreField = new JTextField(3);
		this.scoreField.setBounds(75, 200, 200, 40);

		JButton submit = new JButton("Submit");
		submit.addActionListener(this);
		submit.setBounds(100, 250, 100, 50);

		this.info = new JLabel("");
		this.info.setBounds(75, 300, 300, 50);

		this.searchField = new JTextField(10);
		this.searchField.setBounds(350, 100, 200, 50);

		JButton search = new JButton("Search");
		search.addActionListener(this);
		search.setBounds(400, 170, 100, 50);

		this.searchInfo = new JLabel("");
		this.searchInfo.setBounds(375, 200, 200, 100);

		JButton sort = new JButton("Sort");
		sort.addActionListener(this);
		sort.setBounds(650, 170, 100, 50);

		JButton decrementer = new JButton("Decrement");
		decrementer.addActionListener(this);
		decrementer.setBounds(200, 350, 150, 50);

		JButton incrementer = new JButton("Increment");
		incrementer.addActionListener(this);
		incrementer.setBounds(500, 350, 150, 50);

		this.scoreInfo = new JLabel("");
		this.scoreInfo.setBounds(350, 420, 200, 50);

		frame.add(instruction);
		frame.add(nameLabel);
		frame.add(this.nameField);
		frame.add(rollLabel);
		frame.add(this.rollField);
		frame.add(scoreLabel);
		frame.add(this.scoreField);
		frame.add(this.info);
		frame.add(submit);
		frame.add(this.searchField);
		frame.add(search);
		frame.add(this.searchInfo);
		frame.add(sort);
		frame.add(incrementer);
		frame.add(decrementer);
		frame.add(this.scoreInfo);

		frame.setVisible(true);
	}

	private void addStudent() {
		try {
			String name = this.nameField.getText();
			int roll = Integer.parseInt(this.rollField.getText());
			int score = Integer.parseInt(this.scoreField.getText());
			if (score > 100 || score < 0) {
				throw new CustomException("Invalid score");
			}
			this.students.add(new Student(name, roll, score));
			this.info.setText("Stduent " + name + " has been added!");
		} catch (NumberFormatException e) {
			this.info.setText(e.getMessage());
		} catch (CustomException e) {
			this.info.setText(e.getMessage());
		}
		this.nameField.setText("");
		this.rollField.setText("");
		this.scoreField.setText("");
	}

	private void searchStudent() {
		try {
			String studentName = this.searchField.getText();
			if (studentName.equals("")) {
				throw new CustomException("Invalid input");
			}
			Student s = SearchStudent.search(studentName, this.students);
			String searchResult = new String(
				"<html>Name: " + 
				s.getName() + 
				"<br>Roll: " + 
				s.getRoll() + 
				"<br>Score: " +
				s.getScore() +
				"</html>"
			);
			this.searchInfo.setText(searchResult);
		} catch (CustomException e) {
			this.searchInfo.setText(e.getMessage());
		}
	}

	private void display() {
		try {
			this.students.display();
		} catch (CustomException e) {
			System.out.println(e.getMessage());
		}
	}

	private void incrementStudentScore() {
		StudentScoreModifier scoreModifier = new StudentScoreModifier(this.students);
		ThreadScoreIncrementer th1 = new ThreadScoreIncrementer("Incrementer 1", scoreModifier);
		ThreadScoreIncrementer th2 = new ThreadScoreIncrementer("Incrementer 2", scoreModifier);
		th1.join();
		th2.join();
		this.scoreInfo.setText("Student score(s) incremented");
	}

	private void decrementStudentScore() {
		StudentScoreModifier scoreModifier = new StudentScoreModifier(this.students);
		ThreadScoreDecrementer th1 = new ThreadScoreDecrementer("Decrementer 1", scoreModifier);
		ThreadScoreDecrementer th2 = new ThreadScoreDecrementer("Decrementer 2", scoreModifier);
		th1.join();
		th2.join();
		this.scoreInfo.setText("Student score(s) decremented");
	}

	private void sortStudents() {
		java.util.List<Student> studentsList = new ArrayList<Student>();
		Iterator<Student> itr = this.students.iterator();
		while (itr.hasNext()) {
			studentsList.add(itr.next());
		}
		Collections.sort(studentsList, new CustomSort());
		this.frame.setVisible(false);
		this.frame.dispose();
		itr = studentsList.iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
			if (itr.hasNext()) {
				System.out.println();
			}
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals("Submit")) {
			this.addStudent();
		} else if (ae.getActionCommand().equals("Search")) {
			this.searchStudent();
		} else if (ae.getActionCommand().equals("Increment")) {
			this.incrementStudentScore();
		} else if (ae.getActionCommand().equals("Decrement")) {
			this.decrementStudentScore();
		} else if (ae.getActionCommand().equals("Sort")) {
			this.sortStudents();
		}
	}

}


class Test {
	public static void main(String[] args) {
		new GUIStudent();
	}
}
