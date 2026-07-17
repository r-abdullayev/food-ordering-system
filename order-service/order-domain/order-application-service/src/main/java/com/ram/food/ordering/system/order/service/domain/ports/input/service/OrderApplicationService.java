package com.ram.food.ordering.system.order.service.domain.ports.input.service;

import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {

  CreateOrderResponse createOrder(@Valid CreateOrderRequest createOrderRequest);

  TrackOrderResponse trackOrder(@Valid TrackOrderRequest trackOrderRequest);
}
