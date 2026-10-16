package delivery;

import model.Order;

public interface DeliveryMethod {
  String getName();

  double calculateCost(Order order);

  int getEstimatedDays();

  boolean requiresCourier();
}