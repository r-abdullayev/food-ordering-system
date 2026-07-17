package com.ram.food.ordering.system.order.service.domain.ports.input.service.handler;

import com.ram.food.ordering.system.order.service.domain.ApplicationDomainEventPublisher;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.ram.food.ordering.system.order.service.domain.entity.Customer;
import com.ram.food.ordering.system.order.service.domain.entity.Order;
import com.ram.food.ordering.system.order.service.domain.entity.Restaurant;
import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.ram.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.ram.food.ordering.system.order.service.domain.mapper.OrderMapper;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.ram.food.ordering.system.order.service.domain.service.OrderDomainService;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreateRequestHandler {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final RestaurantRepository restaurantRepository;
  private final ApplicationDomainEventPublisher applicationDomainEventPublisher;

  @Transactional
  public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
    checkCustomer(createOrderRequest.customerId());
    Restaurant restaurant = checkRestaurant(createOrderRequest);
    Order order = OrderMapper.toOrder(createOrderRequest);
    OrderCreatedEvent orderCreatedEvent =
        orderDomainService.validateAndInitiateOrder(order, restaurant);
    Order savedOrder = saveOrder(order);
    applicationDomainEventPublisher.publish(orderCreatedEvent);
    log.info("Order with id: {} was created successfully!", savedOrder.getId().value());
    return OrderMapper.toCreateOrderResponse(savedOrder);
  }

  private void checkCustomer(UUID customerId) {
    Optional<Customer> customer = customerRepository.findCustomer(customerId);
    if (customer.isEmpty()) {
      log.warn("Customer with id: {} was not found!", customerId);
      throw new OrderDomainException("Customer with id: " + customerId + " was not found!");
    }
  }

  private Restaurant checkRestaurant(CreateOrderRequest createOrderRequest) {
    Restaurant restaurant = OrderMapper.toRestaurant(createOrderRequest);
    Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurant(restaurant);
    if (optionalRestaurant.isEmpty()) {
      log.warn("Restaurant with id: {} was not found!", restaurant.getId());
      throw new OrderDomainException(
          "Restaurant with id: " + restaurant.getId() + " was not found!");
    }
    return optionalRestaurant.get();
  }

  private Order saveOrder(Order order) {
    Order savedOrder = orderRepository.save(order);
    if (Objects.isNull(savedOrder)) {
      log.error("Failed to save order!");
      throw new OrderDomainException("Failed to save order!");
    }
    log.info("Order with id: {} was saved successfully!", savedOrder.getId());
    return savedOrder;
  }
}
