package delivery;

import model.Order;

public class PickupDelivery implements DeliveryMethod {
  @Override
  public String getName() {
    return "Самовывоз";
  }

  @Override
  public double calculateCost(Order order) {
    return 0;
  }

  @Override
  public int getEstimatedDays() {
    return 0;
  }

  @Override
  public boolean requiresCourier() {
    return false;
  }
}