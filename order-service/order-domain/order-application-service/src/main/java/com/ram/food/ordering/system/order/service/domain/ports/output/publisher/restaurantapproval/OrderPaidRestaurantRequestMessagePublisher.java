package com.ram.food.ordering.system.order.service.domain.ports.output.publisher.restaurantapproval;

import com.ram.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.ram.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher
    extends DomainEventPublisher<OrderPaidEvent> {}
