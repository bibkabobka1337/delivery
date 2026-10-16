public class Main {
  public static void main(String[] args) throws Exception {
    model.Order order = new model.Order(1, new model.Customer("Иван", "+7900"), "ул. Ленина, 1");
    order.addItem(new model.OrderItem("Пицца", 2, 450));
    order.addItem(new model.OrderItem("Сок", 1, 100));
    System.out.println(order);
    System.out.println(model.OrderStatus.NEW.canTransitionTo(model.OrderStatus.SHIPPED));
  }
}