public class Main {
  public static void main(String[] args) throws Exception {
    model.Order order = new model.Order(1, new model.Customer("Иван", "+7900"), "ул. Ленина, 1");
    model.Courier courier = new model.Courier("Пётр");
    order.addItem(new model.OrderItem("Пицца", 2, 450));
    order.setDeliveryMethod(new delivery.StandardDelivery());
    order.changeStatus(model.OrderStatus.PROCESSING);
    try {
      order.changeStatus(model.OrderStatus.SHIPPED);
    } catch (IllegalStateException e) {
      System.out.println("Ошибка: " + e.getMessage());
    }
    order.assignCourier(courier);
    order.changeStatus(model.OrderStatus.SHIPPED);
    order.changeStatus(model.OrderStatus.DELIVERED);
    System.out.println(order.getStatus() + ", " + courier);
  }
}