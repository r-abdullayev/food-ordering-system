package com.ram.food.ordering.system.order.service.domain.entity;

import com.ram.food.ordering.system.domain.entity.AggregateRoot;
import com.ram.food.ordering.system.domain.value.CustomerId;
import com.ram.food.ordering.system.domain.value.Money;
import com.ram.food.ordering.system.domain.value.OrderId;
import com.ram.food.ordering.system.domain.value.RestaurantId;
import com.ram.food.ordering.system.domain.value.enums.OrderStatus;
import com.ram.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.ram.food.ordering.system.order.service.domain.value.OrderItemId;
import com.ram.food.ordering.system.order.service.domain.value.StreetAddress;
import com.ram.food.ordering.system.order.service.domain.value.TrackingId;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<OrderId> {
  private final CustomerId customerId;
  private final RestaurantId restaurantId;
  private final StreetAddress deliveryAddress;
  private final Money price;
  private final List<OrderItem> orderItems;

  private TrackingId trackingID;
  private OrderStatus orderStatus;
  private List<String> failureMessages;

  public void initializeOrder() {
    setId(new OrderId(UUID.randomUUID()));
    trackingID = new TrackingId(UUID.randomUUID());
    orderStatus = OrderStatus.PENDING;
    initializeOrderItems();
  }

  public void validateOrder() {
    validateInitialOrder();
    validateTotalPrice();
    validateOrderItemsPrice();
  }

  public void pay() {
    if (orderStatus != OrderStatus.PENDING) {
      throw new OrderDomainException("Order is not in correct state for payment");
    }
    orderStatus = OrderStatus.PAID;
  }

  public void approve() {
    if (orderStatus != OrderStatus.PAID) {
      throw new OrderDomainException("Order is not in correct state for approval");
    }
    orderStatus = OrderStatus.APPROVED;
  }

  public void initCancel(List<String> failureMessages) {
    if (orderStatus != OrderStatus.PAID) {
      throw new OrderDomainException("Order is not correct state for cancellation");
    }
    orderStatus = OrderStatus.CANCELLING;
    updateFailureMessages(failureMessages);
  }

  public void cancel(List<String> failureMessages) {
    if (!(orderStatus == OrderStatus.CANCELLING || orderStatus == OrderStatus.PENDING)) {
      throw new OrderDomainException("Order is not in correct state for cancel");
    }
    orderStatus = OrderStatus.CANCELLED;
    updateFailureMessages(failureMessages);
  }

  private void updateFailureMessages(List<String> failureMessages) {
    if (this.failureMessages != null && !failureMessages.isEmpty()) {
      this.failureMessages.addAll(
          failureMessages.stream().filter(failureMessage -> !failureMessage.isBlank()).toList());
    } else if (this.failureMessages == null) {
      this.failureMessages = failureMessages;
    }
  }

  private void validateInitialOrder() {
    if (orderStatus != null || super.getId() != null) {
      throw new OrderDomainException("Order is not in correct state for initialization");
    }
  }

  private void validateTotalPrice() {
    if (price == null || !price.isGreaterThanZero()) {
      throw new OrderDomainException("Total price must be greater than zero");
    }
  }

  private void validateOrderItemsPrice() {
    Money orderItemTotal =
        orderItems.stream()
            .map(
                orderItem -> {
                  validateItemPrice(orderItem);
                  return orderItem.getSubTotal();
                })
            .reduce(Money.ZERO, Money::add);

    if (!price.equals(orderItemTotal)) {
      throw new OrderDomainException(
          "Total price: " + price + " is not equal to order items total: " + orderItemTotal);
    }
  }

  private void validateItemPrice(OrderItem orderItem) {
    if (!orderItem.isPriceValid()) {
      throw new OrderDomainException(
          "Order item price: "
              + orderItem.getPrice()
              + " is not valid for product: "
              + orderItem.getProduct().getName());
    }
  }

  private void initializeOrderItems() {
    long itemId = 1;
    for (OrderItem item : orderItems)
      item.initializeOrderItem(super.getId(), new OrderItemId(itemId++));
  }

  private Order(Builder builder) {
    super.setId(builder.orderId);
    customerId = builder.customerId;
    restaurantId = builder.restaurantId;
    deliveryAddress = builder.deliveryAddress;
    price = builder.price;
    orderItems = builder.orderItems;
    trackingID = builder.trackingID;
    orderStatus = builder.orderStatus;
    failureMessages = builder.failureMessages;
  }

  public static Builder builder() {
    return new Builder();
  }

  public CustomerId getCustomerId() {
    return customerId;
  }

  public RestaurantId getRestaurantId() {
    return restaurantId;
  }

  public StreetAddress getDeliveryAddress() {
    return deliveryAddress;
  }

  public Money getPrice() {
    return price;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public TrackingId getTrackingID() {
    return trackingID;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public List<String> getFailureMessages() {
    return failureMessages;
  }

  public static final class Builder {
    private OrderId orderId;
    private CustomerId customerId;
    private RestaurantId restaurantId;
    private StreetAddress deliveryAddress;
    private Money price;
    private List<OrderItem> orderItems;
    private TrackingId trackingID;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    private Builder() {}

    public Builder orderId(OrderId val) {
      orderId = val;
      return this;
    }

    public Builder customerId(CustomerId val) {
      customerId = val;
      return this;
    }

    public Builder restaurantId(RestaurantId val) {
      restaurantId = val;
      return this;
    }

    public Builder deliveryAddress(StreetAddress val) {
      deliveryAddress = val;
      return this;
    }

    public Builder price(Money val) {
      price = val;
      return this;
    }

    public Builder orderItems(List<OrderItem> val) {
      orderItems = val;
      return this;
    }

    public Builder trackingID(TrackingId val) {
      trackingID = val;
      return this;
    }

    public Builder orderStatus(OrderStatus val) {
      orderStatus = val;
      return this;
    }

    public Builder failureMessages(List<String> val) {
      failureMessages = val;
      return this;
    }

    public Order build() {
      return new Order(this);
    }
  }
}
