
import java.util.*;

class myThread implements Runnable {

  public Thread th;

  public myThread(String name) {
    this.th = new Thread(this, name);
    this.th.start();
  }

  public void run() {
    System.out.printf("%s starting\n", this.th.getName());
    try {
      for (int i=0; i<5; i++) {
        System.out.println(this.th.getName() + "[" + i + "]");
        Thread.sleep(100);
      }
    }
    catch (InterruptedException e) {
      System.out.println(this.th.getName() + " interrupted");
    }
    System.out.println(this.th.getName() + " terminating");
  }
  
}

public class ThreadTest {

  public static void main (String[] args) {
    myThread mt = new myThread("T1");
    System.out.println("Main thread running");
    try {
      mt.th.join();
      System.out.println("T1 joined after termination");
    }
    catch (InterruptedException e) {
      System.out.println("Main thread interrupted");
    }
    System.out.println("Main thread terminating");
  }

}
