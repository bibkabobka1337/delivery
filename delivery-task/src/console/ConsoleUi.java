package console;

import java.util.List;
import java.util.Scanner;

import delivery.DeliveryMethod;
import model.Courier;
import model.Order;
import model.OrderItem;
import model.OrderStatus;
import service.OrderService;

public class ConsoleUi {
  private final Scanner scanner = new Scanner(System.in);
  private final OrderService service;

  public ConsoleUi(OrderService service) {
    this.service = service;
  }

  public void run() {
    boolean running = true;

    while (running) {
      printMenu();
        String choice = scanner.nextLine().trim();
        try {
          switch (choice) {
            case "1" -> createOrder();
            case "2" -> showOrders();
            case "3" -> chooseDelivery();
            case "4" -> assignCourier();
            case "5" -> changeStatus();
            case "0" -> running = false;
            default -> System.out.println("Неизвестная команда, введите число от 0 до 5");
          }
        } catch (IllegalArgumentException | IllegalStateException e) {
          System.out.println("Ошибка: " + e.getMessage());
        }
      }
    System.out.println("Работа завершена");
  }

  private void printMenu() {
    System.out.println();
    System.out.println("=== Служба доставки ===");
    System.out.println("1. Создать заказ");
    System.out.println("2. Показать список заказов");
    System.out.println("3. Выбрать способ доставки");
    System.out.println("4. Назначить курьера");
    System.out.println("5. Изменить статус заказа");
    System.out.println("0. Выход");
    System.out.print("Ваш выбор: ");
  }

  private void showOrders() {
    List<Order> orders = service.getOrders();
    if (orders.isEmpty()) {
      System.out.println("Заказов пока нет");
      return;
    }
    for (Order order : orders) {
      printOrder(order);
    }
  }

  private void printOrder(Order order) {
    System.out.println("----------------------------");
    System.out.println("Заказ №" + order.getNumber());
    System.out.println("Клиент: " + order.getCustomer());
    System.out.println("Адрес: " + order.getAddress());
    System.out.println("Статус: " + order.getStatus());
    for (OrderItem item : order.getItems()) {
      System.out.println("  - " + item);
    }
    if (order.getDeliveryMethod() == null) {
      System.out.println("Доставка: не выбрана");
    } else {
      System.out.println("Доставка: " + order.getDeliveryMethod().getName()
          + ", стоимость " + order.getDeliveryCost()
          + ", срок (дней): " + order.getDeliveryMethod().getEstimatedDays());
    }
    System.out.println("Курьер: "
        + (order.getCourier() == null ? "не назначен" : order.getCourier().getName()));
    System.out.println("Итого: " + order.getTotalCost());
  }

  private void createOrder() {
    System.out.print("Имя клиента: ");
    String name = scanner.nextLine();
    System.out.print("Телефон: ");
    String phone = scanner.nextLine();
    System.out.print("Адрес доставки: ");
    String address = scanner.nextLine();
    Order order = service.createOrder(name, phone, address);
    System.out.println("Создан заказ №" + order.getNumber());

    boolean adding = true;
    while (adding) {
      System.out.print("Название товара: ");
      String product = scanner.nextLine();
      int quantity = readInt("Количество: ");
      double price = readDouble("Цена: ");
      service.addItem(order.getNumber(), product, quantity, price);
      System.out.print("Добавить ещё позицию? (да/нет): ");
      adding = scanner.nextLine().trim().equalsIgnoreCase("да");
    }
  }

  private void chooseDelivery() {
    int number = readInt("Номер заказа: ");
    List<DeliveryMethod> methods = service.getDeliveryMethods();
    for (int i = 0; i < methods.size(); i++) {
      System.out.println((i + 1) + ". " + methods.get(i).getName());
    }
    int index = readInt("Выберите способ: ") - 1;
    service.chooseDelivery(number, index);
    Order order = service.findOrder(number);
    System.out.println("Стоимость доставки: " + order.getDeliveryCost()
        + ", срок (дней): " + order.getDeliveryMethod().getEstimatedDays());
  }

  private void assignCourier() {
    int number = readInt("Номер заказа: ");
    List<Courier> couriers = service.getCouriers();
    for (int i = 0; i < couriers.size(); i++) {
      System.out.println((i + 1) + ". " + couriers.get(i));
    }
    int index = readInt("Выберите курьера: ") - 1;
    service.assignCourier(number, index);
    System.out.println("Курьер назначен");
  }

  private void changeStatus() {
    int number = readInt("Номер заказа: ");
    OrderStatus[] statuses = OrderStatus.values();
    for (int i = 0; i < statuses.length; i++) {
      System.out.println((i + 1) + ". " + statuses[i]);
    }
    int index = readInt("Новый статус: ") - 1;
    if (index < 0 || index >= statuses.length) {
      throw new IllegalArgumentException("Нет статуса с таким номером");
    }
    service.changeStatus(number, statuses[index]);
    printOrder(service.findOrder(number));
  }

  private int readInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Введите целое число");
      }
    }
  }

  private double readDouble(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
      } catch (NumberFormatException e) {
        System.out.println("Введите число");
      }
    }
  }
}