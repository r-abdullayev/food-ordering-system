package com.ram.food.ordering.system.order.service.domain.ports.input.service.handler;

import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.ram.food.ordering.system.order.service.domain.mapper.OrderMapper;
import com.ram.food.ordering.system.order.service.domain.ports.input.service.helper.OrderCreateHelper;
import com.ram.food.ordering.system.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateRequestHandler {

  private final OrderCreateHelper orderCreateHelper;
  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
    OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderRequest);
    log.info("Order with id: {} was created successfully!", orderCreatedEvent.getOrder().getId().value());
    orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    return OrderMapper.toCreateOrderResponse(orderCreatedEvent.getOrder());
  }
}
