package model;

public enum OrderStatus {
  NEW("Новый"),
  PROCESSING("В обработке"),
  SHIPPED("Отправлен"),
  DELIVERED("Доставлен"),
  CANCELLED("Отменён");

  private final String title;

  OrderStatus(String title) {
    this.title = title;
  }

  public boolean canTransitionTo(OrderStatus next) {
    return switch (this) {
      case NEW -> next == PROCESSING || next == CANCELLED;
      case PROCESSING -> next == SHIPPED || next == CANCELLED;
      case SHIPPED -> next == DELIVERED;
      case DELIVERED, CANCELLED -> false;
    };
  }

  @Override
  public String toString() {
    return title;
  }
}