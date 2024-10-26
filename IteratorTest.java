import java.util.*;

class CustomException extends Exception {

  String message;

  CustomException(String message) {
    super(message);
    this.message = message;
  }

  public void getException() {
    System.out.println(this.message);
  }

}

public class IteratorTest {

  public static void main(String[] args) throws CustomException {

    ArrayList<Integer> l = new ArrayList<Integer>();
    l.add(1);
    l.add(2);
    l.add(3);
    l.add(0);
    l.add(4);

    System.out.println(l);

    Iterator<Integer> i = l.iterator();

    try {
      while (i.hasNext()) {
        int t = i.next();
        if (t == 0) {
          throw new CustomException("Zero is not valid input");
        } else if (t == 3) {
          i.remove();
        }
      }
    } catch (CustomException e) {
      e.getException();
    }

    System.out.println(l);

  }

}
