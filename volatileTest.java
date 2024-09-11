
import java.util.*;


class Print {

  static volatile int a = 0, b = 0;

  public static void method_one() {
    a++;
    b++;
  }

  public static void method_two() {
    System.err.printf("a = %d\tb = %d\n", a, b);
  }
}


public class volatileTest {
  public static void main(String[] args) {
    Thread t1 = new Thread() {
      public void run() {
        for (int i=0; i<5; i++) {
          Print.method_one();
        }
      }
    };
    Thread t2 = new Thread() {
      public void run() {
        for (int i=0; i<5; i++) {
          Print.method_two();
        }
      }
    };
    t1.start();
    t2.start();
  }
}
