
import java.util.*;

class Testing {

  public static void main (String[] args) throws CustomException{
    try {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter a number: ");
      int n = sc.nextInt();
      if (n == 0) {
        throw new CustomException("Invalid number");
      }
      else {
        System.out.println("Hello World!");
      }
    }
    catch(CustomException e) {
      System.out.println("ERROR");
      System.out.println(e.getMessage());
    }
  }

}
