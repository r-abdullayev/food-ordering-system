package com.ram.food.ordering.system.order.service.domain.ports.input.service.handler;

import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.ram.food.ordering.system.order.service.domain.mapper.OrderMapper;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.ram.food.ordering.system.order.service.domain.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderTrackRequestHandler {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final RestaurantRepository restaurantRepository;
  private final OrderMapper mapper;

  public TrackOrderResponse trackOrder(TrackOrderRequest trackOrderRequest) {
    return null;
  }
}
