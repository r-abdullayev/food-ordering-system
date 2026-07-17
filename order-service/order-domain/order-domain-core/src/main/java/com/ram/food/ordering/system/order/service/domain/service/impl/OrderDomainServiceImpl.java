package com.ram.food.ordering.system.order.service.domain.service.impl;

import com.ram.food.ordering.system.order.service.domain.entity.Order;
import com.ram.food.ordering.system.order.service.domain.entity.Product;
import com.ram.food.ordering.system.order.service.domain.entity.Restaurant;
import com.ram.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.ram.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.ram.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.ram.food.ordering.system.order.service.domain.service.OrderDomainService;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class OrderDomainServiceImpl implements OrderDomainService {

  private static final String UTC_ZONE = "UTC";

  @Override
  public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
    validateRestaurant(restaurant);
    setOrderProductInformation(order, restaurant);
    order.validateOrder();
    order.initializeOrder();
    log.info("Order with id '{}' is initialized", order.getId().value());
    return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC_ZONE)));
  }

  @Override
  public OrderPaidEvent payOrder(Order order) {
    order.pay();
    log.info("Order with id: {} is paid", order.getId().value());
    return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC_ZONE)));
  }

  @Override
  public void approveOrder(Order order) {
    order.approve();
    log.info("Order with id: {} is approved", order.getId().value());
  }

  @Override
  public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
    order.initCancel(failureMessage);
    log.info("Order payment is cancelling for order id: {}", order.getId().value());
    return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC_ZONE)));
  }

  @Override
  public void cancelOrder(Order order) {
    order.cancel(order.getFailureMessages());
    log.info("Order with id: {} is cancelled", order.getId().value());
  }

  private void validateRestaurant(Restaurant restaurant) {
    if (!restaurant.isActive()) {
      throw new OrderDomainException(
          "Restaurant with id: " + restaurant.getId() + " is currently not active");
    }
  }

  private void setOrderProductInformation(Order order, Restaurant restaurant) {
    Map<Product, Product> productsByKey =
        restaurant.getProducts().stream()
            .collect(Collectors.toMap(Function.identity(), Function.identity()));

    order
        .getOrderItems()
        .forEach(
            orderItem -> {
              Product confirmed = productsByKey.get(orderItem.getProduct());
              if (confirmed != null) {
                orderItem
                    .getProduct()
                    .updateWithConfirmedNameAndPrice(confirmed.getName(), confirmed.getPrice());
              }
            });
  }
}
