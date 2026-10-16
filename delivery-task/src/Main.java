import console.ConsoleUi;
import service.OrderService;

public class Main {
  public static void main(String[] args) {
    new ConsoleUi(new OrderService()).run();
  }
}