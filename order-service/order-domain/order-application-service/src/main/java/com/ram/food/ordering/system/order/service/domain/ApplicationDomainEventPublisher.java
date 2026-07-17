package com.ram.food.ordering.system.order.service.domain;

import com.ram.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationDomainEventPublisher
    implements ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {

  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void setApplicationEventPublisher(
      @NonNull ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Override
  public void publish(OrderCreatedEvent orderCreatedEvent) {
    this.applicationEventPublisher.publishEvent(orderCreatedEvent);
    log.info(
        "Order created event published for order id: {}",
        orderCreatedEvent.getOrder().getId().value());
  }
}
