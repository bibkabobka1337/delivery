# Delivery Simulator

Консольный симулятор службы доставки.

## Запуск

Откройте проект в VS Code и запустите `Main.java`.

## Модель

### Сущности

| Класс | Поля | Поведение |
|---|---|---|
| `Customer` | `name`, `phone` | проверка данных в конструкторе, `toString` |
| `Courier` | `name`, `available` | `isAvailable()`, `markBusy()`, `markFree()` |
| `OrderItem` | `productName`, `quantity`, `price` | проверка данных (количество > 0, цена > 0), `getTotal()` |
| `OrderStatus` (enum) | `NEW`, `PROCESSING`, `SHIPPED`, `DELIVERED`, `CANCELLED` | `canTransitionTo(next)` |
| `Order` | `number`, `customer`, `address`, `items`, `deliveryMethod`, `courier`, `status` | `addItem`, `setDeliveryMethod`, `assignCourier`, `changeStatus`, `getTotalCost` |
| `DeliveryMethod` (интерфейс) | нет | `calculateCost(order)`, `getEstimatedDays()`, `requiresCourier()`, `getName()` |
| `StandardDelivery`, `ExpressDelivery`, `PickupDelivery` | нет | реализуют интерфейс |
| `OrderService` | список заказов, счётчик номеров | вся бизнес-логика |
| `ConsoleUi` | `Scanner`, `OrderService` | меню, ввод, вывод |

Для денег берём `double`. Для учебного проекта этого хватает.

### Связи

```
Order ──содержит──> Customer
Order ──содержит──> List<OrderItem>
Order ──содержит──> DeliveryMethod  (интерфейс)
Order ──ссылается─> Courier         (может быть null)

DeliveryMethod <── StandardDelivery, ExpressDelivery, PickupDelivery

ConsoleUi ──использует──> OrderService ──управляет──> Order
```