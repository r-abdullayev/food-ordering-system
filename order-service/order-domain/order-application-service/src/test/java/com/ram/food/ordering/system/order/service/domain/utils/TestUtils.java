package com.ram.food.ordering.system.order.service.domain.utils;

import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import com.ram.food.ordering.system.order.service.domain.dto.create.OrderItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

  public static final UUID CUSTOMER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
  public static final UUID RESTAURANT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb45");
  public static final UUID PRODUCT_ID_1 = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
  public static final UUID PRODUCT_ID_2 = UUID.fromString("d315b5f8-0249-4dc5-89a3-51fd148cfb84");
  public static final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");
  public static final UUID SAGA_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afa");
  public static final BigDecimal PRICE = new BigDecimal("200.00");

  public static CreateOrderRequest createOrderRequestValid() {
    return CreateOrderRequest.builder()
        .customerId(CUSTOMER_ID)
        .restaurantId(RESTAURANT_ID)
        .address(
            OrderAddress.builder().street("street_1").postalCode("1000AB").city("Paris").build())
        .price(PRICE)
        .orderItems(
            List.of(
                OrderItem.builder()
                    .productId(PRODUCT_ID_1)
                    .quantity(1)
                    .price(new BigDecimal("50.00"))
                    .subTotal(new BigDecimal("50.00"))
                    .build(),
                OrderItem.builder()
                    .productId(PRODUCT_ID_2)
                    .quantity(3)
                    .price(new BigDecimal("50.00"))
                    .subTotal(new BigDecimal("150.00"))
                    .build()))
        .build();
  }

  public static CreateOrderRequest createOrderRequestWrongPrice() {
    return CreateOrderRequest.builder()
        .customerId(CUSTOMER_ID)
        .restaurantId(RESTAURANT_ID)
        .address(
            OrderAddress.builder().street("street_1").postalCode("1000AB").city("Paris").build())
        .price(new BigDecimal("250.00"))
        .orderItems(
            List.of(
                OrderItem.builder()
                    .productId(PRODUCT_ID_1)
                    .quantity(1)
                    .price(new BigDecimal("50.00"))
                    .subTotal(new BigDecimal("50.00"))
                    .build(),
                OrderItem.builder()
                    .productId(PRODUCT_ID_2)
                    .quantity(3)
                    .price(new BigDecimal("50.00"))
                    .subTotal(new BigDecimal("150.00"))
                    .build()))
        .build();
  }

  public static CreateOrderRequest createOrderRequestWrongProductPrice() {
    return CreateOrderRequest.builder()
        .customerId(CUSTOMER_ID)
        .restaurantId(RESTAURANT_ID)
        .address(
            OrderAddress.builder().street("street_1").postalCode("1000AB").city("Paris").build())
        .price(new BigDecimal("210.00"))
        .orderItems(
            List.of(
                OrderItem.builder()
                    .productId(PRODUCT_ID_1)
                    .quantity(1)
                    .price(new BigDecimal("60.00"))
                    .subTotal(new BigDecimal("60.00"))
                    .build(),
                OrderItem.builder()
                    .productId(PRODUCT_ID_2)
                    .quantity(3)
                    .price(new BigDecimal("50.00"))
                    .subTotal(new BigDecimal("150.00"))
                    .build()))
        .build();
  }
}
