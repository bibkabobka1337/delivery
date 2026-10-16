public class Main {
  public static void main(String[] args) throws Exception {
    model.Order order = new model.Order(1, new model.Customer("Иван", "+7900"), "ул. Ленина, 1");
    order.addItem(new model.OrderItem("Пицца", 2, 450));
    order.setDeliveryMethod(new delivery.ExpressDelivery());
    System.out.println(order);
    order.setDeliveryMethod(new delivery.PickupDelivery());
    System.out.println(order);
  }
}