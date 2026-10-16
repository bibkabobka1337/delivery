package delivery;

import model.Order;

public class ExpressDelivery implements DeliveryMethod {
  private static final double BASE_COST = 500;
  private static final double PERCENT = 0.05;

  @Override
  public String getName() {
    return "Экспресс-доставка";
  }

  @Override
  public double calculateCost(Order order) {
    return BASE_COST + order.getItemsTotal() * PERCENT;
  }

  @Override
  public int getEstimatedDays() {
    return 1;
  }

  @Override
  public boolean requiresCourier() {
    return true;
  }
}