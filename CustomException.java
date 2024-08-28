
import java.util.*;


public class CustomeException extends Exception{
  
  public CustomeException(String s) {
      super(s);
  }

}


class Testing {

  public static void main (String args) throws CustomeException{
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    if (n == 0) {
      CustomeException("Invalid number")
    }
    else {
      System.out.println("Hello World!");
    }
  }

}
