import java.util.*;

class Student {

  private String name;
  private int roll;
  private int[] marks;

  Student(String name, int roll, int[] marks) {
    this.name = name;
    this.roll = roll;
    this.marks = marks;
  }

  public int[] getMarks() {
    return this.marks;
  }

  @Override
  public String toString() {
    String name = "Name: " + this.name + "\n";
    String roll = "Roll: " + this.roll + "\n";
    String marks = "Marks: ";
    for (int i = 0; i < this.marks.length; i++) {
      marks += this.marks[i];
      if (i < this.marks.length - 1) {
        marks += ", ";
      }
    }
    return name + roll + marks;
  }

}

public class CustomSort implements Comparator<Student> {
  @Override
  public int compare(Student a, Student b) {
    int marks1 = sum(a);
    int marks2 = sum(b);
    return marks1 - marks2;
  }

  public int sum(Student s) {
    int summation = 0;
    for (int num : s.getMarks()) {
      summation += num;
    }
    return summation;
  }
}

class CustomSortTest {
  public static void main(String[] args) {
    List<Student> students = new ArrayList<Student>();

    students.add(new Student("A", 10, new int[] { 1, 2, 30, 4 }));
    students.add(new Student("B", 20, new int[] { 10, 2, 3, 4 }));
    students.add(new Student("C", 30, new int[] { 1, 20, 3, 4 }));
    students.add(new Student("D", 40, new int[] { 1, 2, 3, 40 }));

    Collections.sort(students, new CustomSort());

    for (Student s : students) {
      System.out.println(s);
    }
  }
}
