package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

  @Override
  public String toString() {
    return "Заказ №" + number + ", клиент: " + customer + ", статус: " + status
        + ", сумма: " + getItemsTotal();
  }
}