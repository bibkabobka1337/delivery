package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import delivery.DeliveryMethod;

public class Order {
  private final int number;
  private final Customer customer;
  private final String address;
  private final List<OrderItem> items = new ArrayList<>();
  private OrderStatus status = OrderStatus.NEW;

  public Order(int number, Customer customer, String address) {
    if (customer == null) {
      throw new IllegalArgumentException("У заказа должен быть клиент");
    }
    if (address == null || address.isBlank()) {
      throw new IllegalArgumentException("Адрес доставки не может быть пустым");
    }
    this.number = number;
    this.customer = customer;
    this.address = address.trim();
  }

  public void addItem(OrderItem item) {
    if (item == null) {
      throw new IllegalArgumentException("Позиция не может быть пустой");
    }
    if (status != OrderStatus.NEW) {
      throw new IllegalStateException(
          "Добавлять позиции можно только в новый заказ, сейчас статус: " + status);
    }
    items.add(item);
  }

  public void setDeliveryMethod(DeliveryMethod method) {
    if (method == null) {
      throw new IllegalArgumentException("Способ доставки не выбран");
    }
    if (status != OrderStatus.NEW) {
      throw new IllegalStateException(
          "Менять способ доставки можно только в новом заказе, сейчас статус: " + status);
    }
    if (courier != null && !method.requiresCourier()) {
      courier.markFree();
      courier = null;
    }
    this.deliveryMethod = method;
  }

  public void assignCourier(Courier courier) {
    if (courier == null) {
      throw new IllegalArgumentException("Курьер не указан");
    }
    if (deliveryMethod == null) {
      throw new IllegalStateException("Сначала выберите способ доставки");
    }
    if (!deliveryMethod.requiresCourier()) {
      throw new IllegalStateException(
          "Для способа \"" + deliveryMethod.getName() + "\" курьер не нужен");
    }
    if (status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED) {
      throw new IllegalStateException("Нельзя назначить курьера в заказ со статусом: " + status);
    }
    if (this.courier != null) {
      throw new IllegalStateException("Курьер уже назначен: " + this.courier.getName());
    }
    courier.markBusy();
    this.courier = courier;
  }

  public void changeStatus(OrderStatus next) {
    if (next == null) {
      throw new IllegalArgumentException("Статус не указан");
    }
    if (!status.canTransitionTo(next)) {
      throw new IllegalStateException(
          "Переход из статуса \"" + status + "\" в \"" + next + "\" недопустим");
    }
    if (next == OrderStatus.PROCESSING || next == OrderStatus.SHIPPED) {
      checkReadyForProcessing();
    }
    if (next == OrderStatus.SHIPPED && deliveryMethod.requiresCourier() && courier == null) {
      throw new IllegalStateException("Нельзя отправить заказ без назначенного курьера");
    }
    status = next;
    if ((next == OrderStatus.DELIVERED || next == OrderStatus.CANCELLED) && courier != null) {
      courier.markFree();
    }
  }

  private void checkReadyForProcessing() {
    if (items.isEmpty()) {
      throw new IllegalStateException("В заказе нет позиций");
    }
    if (deliveryMethod == null) {
      throw new IllegalStateException("Не выбран способ доставки");
    }
  }

  public Courier getCourier() {
    return courier;
  }

  public DeliveryMethod getDeliveryMethod() {
    return deliveryMethod;
  }

  public double getDeliveryCost() {
    return deliveryMethod == null ? 0 : deliveryMethod.calculateCost(this);
  }

  public double getTotalCost() {
    return getItemsTotal() + getDeliveryCost();
  }

  public double getItemsTotal() {
    double sum = 0;
    for (OrderItem item : items) {
      sum += item.getTotal();
    }
    return sum;
  }

  public int getNumber() {
    return number;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getAddress() {
    return address;
  }

  public List<OrderItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public OrderStatus getStatus() {
    return status;
  }

  private DeliveryMethod deliveryMethod;

  private Courier courier;

  @Override
  public String toString() {
    return "Заказ №" + number + ", клиент: " + customer + ", статус: " + status
        + ", сумма: " + getTotalCost();
  }
}