package com.ram.food.ordering.system.order.service.domain;

import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.ram.food.ordering.system.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationDomainEventListener {

  private final OrderCreatedPaymentRequestMessagePublisher
      orderCreatedPaymentRequestMessagePublisher;

  @TransactionalEventListener
  void process(OrderCreatedEvent orderCreatedEvent) {}
}
