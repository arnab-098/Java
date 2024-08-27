
import java.util.*;

class Parse {

  static String expr;
  static int idx, size;

  private static void initialize() {
    idx = 0;
    size = expr.length();
  }

  private static String findToken() {
    String token = new String();
    while (idx < size && expr.charAt(idx) == ' ') {
      idx++;
    }
    while (idx < size && expr.charAt(idx) != ' ') {
      token += expr.charAt(idx++);
    }
    if (token.length() == 0) {
      return null;
    }
    return token;
  }

  public static ArrayList<String> parse(String expression) {
    expr = expression;
    initialize();
    ArrayList<String> tokens = new ArrayList<String>();
    while (idx < size) {
      String token = findToken();
      if (token != null) {
        tokens.add(token);
      }
    }
    return tokens;
  }

}

  public static void main(String[] args) {
    System.out.print("Enter the expression: ");
    Scanner sc = new Scanner(System.in);
    String expr = sc.nextLine();
    ArrayList<String> result = Parse.parse(expr);
    for (String x: result) {
      System.out.println(x);
    }
  }

}
