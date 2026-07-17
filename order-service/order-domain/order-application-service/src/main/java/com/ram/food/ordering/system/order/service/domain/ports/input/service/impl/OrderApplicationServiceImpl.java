package com.ram.food.ordering.system.order.service.domain.ports.input.service.impl;

import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.ram.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.ram.food.ordering.system.order.service.domain.ports.input.service.handler.OrderCreateRequestHandler;
import com.ram.food.ordering.system.order.service.domain.ports.input.service.handler.OrderTrackRequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
@RequiredArgsConstructor
class OrderApplicationServiceImpl implements OrderApplicationService {

  private final OrderCreateRequestHandler orderCreateRequestHandler;
  private final OrderTrackRequestHandler orderTrackRequestHandler;

  @Override
  public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
    return orderCreateRequestHandler.createOrder(createOrderRequest);
  }

  @Override
  public TrackOrderResponse trackOrder(TrackOrderRequest trackOrderRequest) {
    return orderTrackRequestHandler.trackOrder(trackOrderRequest);
  }
}
