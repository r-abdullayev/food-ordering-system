package com.ram.food.ordering.system.order.service.domain.ports.output.repository;

import com.ram.food.ordering.system.order.service.domain.entity.Order;
import com.ram.food.ordering.system.order.service.domain.value.TrackingId;
import java.util.Optional;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findByTrackingId(TrackingId trackingId);
}
