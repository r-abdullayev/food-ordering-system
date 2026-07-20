package com.ram.food.ordering.system.order.service.domain.ports.input.service.handler;

import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.ram.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.ram.food.ordering.system.order.service.domain.mapper.OrderMapper;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.ram.food.ordering.system.order.service.domain.value.TrackingId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTrackRequestHandler {

  private final OrderRepository orderRepository;

  @Transactional(readOnly = true)
  public TrackOrderResponse trackOrder(TrackOrderRequest trackOrderRequest) {
    return orderRepository
        .findByTrackingId(new TrackingId(trackOrderRequest.orderTrackingId()))
        .map(OrderMapper::toTrackOrderResponse)
        .orElseThrow(
            () -> {
              log.warn(
                  "Cannot find order with tracking id: {}", trackOrderRequest.orderTrackingId());
              return new OrderNotFoundException(
                  "Cannot find order with tracking id: " + trackOrderRequest.orderTrackingId());
            });
  }
}
