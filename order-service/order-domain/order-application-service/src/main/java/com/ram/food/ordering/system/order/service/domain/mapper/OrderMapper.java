package com.ram.food.ordering.system.order.service.domain.mapper;

import com.ram.food.ordering.system.domain.value.CustomerId;
import com.ram.food.ordering.system.domain.value.Money;
import com.ram.food.ordering.system.domain.value.ProductId;
import com.ram.food.ordering.system.domain.value.RestaurantId;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.ram.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.ram.food.ordering.system.order.service.domain.entity.Order;
import com.ram.food.ordering.system.order.service.domain.entity.OrderItem;
import com.ram.food.ordering.system.order.service.domain.entity.Product;
import com.ram.food.ordering.system.order.service.domain.entity.Restaurant;
import com.ram.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.ram.food.ordering.system.order.service.domain.value.StreetAddress;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class OrderMapper {

  public Restaurant toRestaurant(CreateOrderRequest createOrderRequest) {
    return Restaurant.builder()
        .id(new RestaurantId(createOrderRequest.restaurantId()))
        .products(
            createOrderRequest.orderItems().stream()
                .map(orderItem -> new Product(new ProductId(orderItem.productId())))
                .toList())
        .build();
  }

  public Order toOrder(CreateOrderRequest createOrderRequest) {
    return Order.builder()
        .customerId(new CustomerId(createOrderRequest.customerId()))
        .restaurantId(new RestaurantId(createOrderRequest.restaurantId()))
        .deliveryAddress(toStreetAddress(createOrderRequest.address()))
        .price(new Money(createOrderRequest.price()))
        .orderItems(toOrderItems(createOrderRequest.orderItems()))
        .build();
  }

  public CreateOrderResponse toCreateOrderResponse(Order order) {
    return CreateOrderResponse.builder()
        .orderTrackingId(order.getId().value())
        .orderStatus(order.getOrderStatus())
        .build();
  }

  private List<OrderItem> toOrderItems(
      List<com.ram.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
    return orderItems.stream()
        .map(orderItem -> OrderItem.builder()
            .product(new Product(new ProductId(orderItem.productId())))
            .quantity(orderItem.quantity())
            .price(new Money(orderItem.price()))
            .subTotal(new Money(orderItem.subTotal()))
            .build())
        .toList();
  }

  private StreetAddress toStreetAddress(OrderAddress address) {
    return new StreetAddress(
        UUID.randomUUID(),
        address.street(),
        address.postalCode(),
        address.city()
    );
  }
}
