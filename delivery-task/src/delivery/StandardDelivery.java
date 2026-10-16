package delivery;

import model.Order;

public class StandardDelivery implements DeliveryMethod {
  private static final double COST = 200;

  @Override
  public String getName() {
    return "Стандартная доставка курьером";
  }

  @Override
  public double calculateCost(Order order) {
    return COST;
  }

  @Override
  public int getEstimatedDays() {
    return 3;
  }

  @Override
  public boolean requiresCourier() {
    return true;
  }
}