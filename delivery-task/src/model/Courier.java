package model;

public class Courier {
  private final String name;
  private boolean available = true;

  public Courier(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Имя курьера не может быть пустым");
    }
    this.name = name.trim();
  }

  public String getName() {
    return name;
  }

  public boolean isAvailable() {
    return available;
  }

  public void markBusy() {
    if (!available) {
      throw new IllegalStateException("Курьер " + name + " уже занят");
    }
    available = false;
  }

  public void markFree() {
    available = true;
  }

  @Override
  public String toString() {
    return name + (available ? " (свободен)" : " (занят)");
  }
}