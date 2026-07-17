package com.ram.food.ordering.system.order.service.domain.ports.output.publisher.payment;

import com.ram.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.ram.food.ordering.system.order.service.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher
    extends DomainEventPublisher<OrderCancelledEvent> {}
