package com.ram.food.ordering.system.order.service.domain.service;

import com.ram.food.ordering.system.order.service.domain.entity.Order;
import com.ram.food.ordering.system.order.service.domain.entity.Restaurant;
import com.ram.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.ram.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import java.util.List;

public interface OrderDomainService {

  OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

  OrderPaidEvent payOrder(Order order);

  void approveOrder(Order order);

  OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);

  void cancelOrder(Order order);
}
