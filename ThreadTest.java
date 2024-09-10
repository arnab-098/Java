
import java.util.*;


class Print {

  private int s;

  synchronized public int sum() {
    s = 0;
    for (int i=0; i<10; i++) {
      s += i;
      System.out.println(Thread.currentThread().getName() + " added " + i + "\tTotal: " + s);
    }
    try {Thread.sleep(10);}
    catch (InterruptedException e) {
      System.out.println(Thread.currentThread().getName() + " interrupted");
    }
    return s;
  }

}


class myThread implements Runnable {

  public Thread th;
  static Print p = new Print();
  private int result;

  public myThread(String name) {
    this.th = new Thread(this, name);
    this.th.start();
  }

  public void run() {
    System.out.printf("%s starting\n", this.th.getName());
    this.result = p.sum();
    System.out.println(this.th.getName() + " Result: " + result);
    System.out.println(this.th.getName() + " terminating");
  }

  public int getResult() {
    return this.result;
  }
  
}


public class ThreadTest {

  public static void main (String[] args) {
    System.out.println("Main thread running");
    myThread th1 = new myThread("T1");
    myThread th2 = new myThread("T2");
    try {
      th1.th.join();
      System.out.println("T1 joined after termination");
      th2.th.join();
      System.out.println("T2 joined after termination");
    }
    catch (InterruptedException e) {
      System.out.println("Main thread interrupted");
    }
    System.out.println("Result: " + (th1.getResult() + th2.getResult()));
    System.out.println("Main thread terminating");
  }

}
