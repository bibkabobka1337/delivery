package service;

import delivery.DeliveryMethod;
import delivery.ExpressDelivery;
import delivery.PickupDelivery;
import delivery.StandardDelivery;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Courier;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.OrderStatus;

public class OrderService {
  private final List<Order> orders = new ArrayList<>();
  private final List<Courier> couriers = new ArrayList<>();
  private final List<DeliveryMethod> deliveryMethods = List.of(
      new StandardDelivery(),
      new ExpressDelivery(),
      new PickupDelivery());
  private int nextNumber = 1;

  public OrderService() {
    couriers.add(new Courier("Пётр"));
    couriers.add(new Courier("Анна"));
    couriers.add(new Courier("Сергей"));
  }

  public Order createOrder(String customerName, String phone, String address) {
    Order order = new Order(nextNumber, new Customer(customerName, phone), address);
    nextNumber++;
    orders.add(order);
    return order;
  }

  public void addItem(int orderNumber, String product, int quantity, double price) {
    findOrder(orderNumber).addItem(new OrderItem(product, quantity, price));
  }

  public void chooseDelivery(int orderNumber, int methodIndex) {
    if (methodIndex < 0 || methodIndex >= deliveryMethods.size()) {
      throw new IllegalArgumentException("Нет способа доставки с таким номером");
    }
    findOrder(orderNumber).setDeliveryMethod(deliveryMethods.get(methodIndex));
  }

  public void assignCourier(int orderNumber, int courierIndex) {
    if (courierIndex < 0 || courierIndex >= couriers.size()) {
      throw new IllegalArgumentException("Нет курьера с таким номером");
    }
    findOrder(orderNumber).assignCourier(couriers.get(courierIndex));
  }

  public void changeStatus(int orderNumber, OrderStatus status) {
    findOrder(orderNumber).changeStatus(status);
  }

  public Order findOrder(int orderNumber) {
    for (Order order : orders) {
      if (order.getNumber() == orderNumber) {
        return order;
      }
    }
    throw new IllegalArgumentException("Заказ №" + orderNumber + " не найден");
  }

  public List<Order> getOrders() {
    return Collections.unmodifiableList(orders);
  }

  public List<Courier> getCouriers() {
    return Collections.unmodifiableList(couriers);
  }

  public List<DeliveryMethod> getDeliveryMethods() {
    return deliveryMethods;
  }
}