import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class myThread implements Runnable {

  Thread thrd;
  int[] nums;
  int s;

  myThread(String name, int[] arr) {
    this.nums = arr;
    this.s = 0;
    thrd = new Thread(this, name);
    thrd.start();
  }

  public void run() {
    System.out.println(thrd.getName() + " is running");
    for (int i=0; i<nums.length; i++) {
      this.s += nums[i];
    }
  }

  public int getSum() {
    System.out.println(this.thrd.getName() + ": " + this.s);
    return this.s;
  }

}

public class GUITest implements ActionListener {

  JLabel label;
  JTextField text;
  int[] values;
  int size, index;

  GUITest() {
    this.size = 100;
    this.index = 0;
    this.values = new int[size];

    JFrame frm = new JFrame();
    frm.setSize(300, 300);
    frm.setLayout(new FlowLayout());
    frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    label = new JLabel("                                ");
    text = new JTextField(5);
    
    JButton btn1 = new JButton("Add");
    JButton btn2 = new JButton("Equal");
    btn1.addActionListener(this);
    btn2.addActionListener(this);

    frm.add(text);
    frm.add(btn1);
    frm.add(btn2);
    frm.add(label);
    frm.setVisible(true);
  }

  private void changeLabel() {
    try {
      int n = Integer.parseInt(this.text.getText());
      System.out.println(n);
      this.values[this.index++] = n;
    } catch (NumberFormatException e) {
      this.label.setText(e.getMessage());
    }
    this.text.setText("");
  }

  private void summation(int[] n1, int[] n2) {
    myThread m1 = new myThread("T1", n1);
    myThread m2 = new myThread("T2", n2);
    try {
      m1.thrd.join();
      System.out.println(m1.thrd.getName() + " joined");
      m2.thrd.join();
      System.out.println(m2.thrd.getName() + " joined");
    } catch (InterruptedException e) {
      System.out.println("Thread inturrupted");
      return;
    }
    this.label.setText("Sum: " + (m1.getSum() + m2.getSum()));
  }

  private void addPressed() {
    if (this.index == this.size-1) {
      this.label.setText("No more values can be added!");
      return;
    }
    this.changeLabel();
  }

  private void eqaulPressed() {
    if (this.index == 0) {
      this.label.setText("Sum: 0");
      return;
    }
    int pos = this.index/2;
    int[] n1 = new int[pos];
    int[] n2 = new int[this.index - pos];
    n1 = Arrays.copyOfRange(this.values, 0, pos);
    n2 = Arrays.copyOfRange(this.values, pos, this.index);
    this.summation(n1, n2);
  }

  public void actionPerformed(ActionEvent ae) {
    if (ae.getActionCommand().equals("Add")) {
      this.addPressed();
    } else if (ae.getActionCommand().equals("Equal")) {
      this.eqaulPressed();
    }
  }

  public static void main(String[] args) {
    new GUITest();
  }

}
