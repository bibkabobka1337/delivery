public class Main {
  public static void main(String[] args) throws Exception {
    service.OrderService s = new service.OrderService();
    model.Order o = s.createOrder("Иван", "+7900", "ул. Ленина, 1");
    s.addItem(o.getNumber(), "Пицца", 2, 450);
    s.chooseDelivery(o.getNumber(), 1);
    s.assignCourier(o.getNumber(), 0);
    s.changeStatus(o.getNumber(), model.OrderStatus.PROCESSING);
    System.out.println(s.getOrders());
    System.out.println(s.getCouriers());
    try {
      s.findOrder(99);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }
}