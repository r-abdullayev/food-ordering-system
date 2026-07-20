package com.ram.food.ordering.system.order.service.domain.ports.input.service.impl;

import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.CUSTOMER_ID;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.ORDER_ID;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.PRODUCT_ID_1;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.PRODUCT_ID_2;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.RESTAURANT_ID;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.createOrderRequestValid;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.createOrderRequestWrongPrice;
import static com.ram.food.ordering.system.order.service.domain.utils.TestUtils.createOrderRequestWrongProductPrice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ram.food.ordering.system.domain.value.CustomerId;
import com.ram.food.ordering.system.domain.value.Money;
import com.ram.food.ordering.system.domain.value.OrderId;
import com.ram.food.ordering.system.domain.value.ProductId;
import com.ram.food.ordering.system.domain.value.RestaurantId;
import com.ram.food.ordering.system.domain.value.enums.OrderStatus;
import com.ram.food.ordering.system.order.service.domain.OrderTestConfiguration;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderRequest;
import com.ram.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.ram.food.ordering.system.order.service.domain.entity.Customer;
import com.ram.food.ordering.system.order.service.domain.entity.Order;
import com.ram.food.ordering.system.order.service.domain.entity.Product;
import com.ram.food.ordering.system.order.service.domain.entity.Restaurant;
import com.ram.food.ordering.system.order.service.domain.exception.OrderDomainException;
import com.ram.food.ordering.system.order.service.domain.mapper.OrderMapper;
import com.ram.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = OrderTestConfiguration.class)
class OrderApplicationServiceTest {

  @Autowired private OrderApplicationService orderApplicationService;

  @Autowired private OrderRepository orderRepository;

  @Autowired private CustomerRepository customerRepository;

  @Autowired private RestaurantRepository restaurantRepository;

  private CreateOrderRequest createOrderRequest;
  private CreateOrderRequest createOrderRequestWrongPrice;
  private CreateOrderRequest createOrderRequestWrongProductPrice;

  @BeforeAll
  void init() {
    createOrderRequest = createOrderRequestValid();
    createOrderRequestWrongPrice = createOrderRequestWrongPrice();
    createOrderRequestWrongProductPrice = createOrderRequestWrongProductPrice();

    Customer customer = new Customer(new CustomerId(CUSTOMER_ID));

    Restaurant restaurantResponse =
        Restaurant.builder()
            .restaurantId(new RestaurantId(createOrderRequest.restaurantId()))
            .products(
                List.of(
                    new Product(
                        new ProductId(PRODUCT_ID_1),
                        "product-1",
                        new Money(new BigDecimal("50.00"))),
                    new Product(
                        new ProductId(PRODUCT_ID_2),
                        "product-2",
                        new Money(new BigDecimal("50.00")))))
            .active(true)
            .build();

    Order order = OrderMapper.toOrder(createOrderRequest);
    order.setId(new OrderId(ORDER_ID));

    when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
    when(restaurantRepository.findRestaurant(OrderMapper.toRestaurant(createOrderRequest)))
        .thenReturn(Optional.of(restaurantResponse));
    when(orderRepository.save(any(Order.class))).thenReturn(order);
  }

  @Test
  void testCreateOrder() {
    CreateOrderResponse createOrderResponse =
        orderApplicationService.createOrder(createOrderRequest);
    assertEquals(OrderStatus.PENDING, createOrderResponse.orderStatus());
    assertEquals("Order created successfully", createOrderResponse.message());
    assertNotNull(createOrderResponse.orderTrackingId());
  }

  @Test
  void testCreateOrderWithWrongTotalPrice() {
    OrderDomainException orderDomainException =
        assertThrows(
            OrderDomainException.class,
            () -> orderApplicationService.createOrder(createOrderRequestWrongPrice));
    assertEquals(
        "Total price: 250.00 is not equal to order items total: 200.00",
        orderDomainException.getMessage());
  }

  @Test
  void testCreateOrderWithWrongProductPrice() {
    OrderDomainException orderDomainException =
        assertThrows(
            OrderDomainException.class,
            () -> orderApplicationService.createOrder(createOrderRequestWrongProductPrice));
    assertEquals(
        "Order item price: 60.00 is not valid for product: " + PRODUCT_ID_1,
        orderDomainException.getMessage());
  }

  @Test
  void testCreateOrderWithPassiveRestaurant() {
    Restaurant restaurantResponse =
        Restaurant.builder()
            .restaurantId(new RestaurantId(createOrderRequest.restaurantId()))
            .products(
                List.of(
                    new Product(
                        new ProductId(PRODUCT_ID_1),
                        "product-1",
                        new Money(new BigDecimal("50.00"))),
                    new Product(
                        new ProductId(PRODUCT_ID_2),
                        "product-2",
                        new Money(new BigDecimal("50.00")))))
            .active(false)
            .build();
    when(restaurantRepository.findRestaurant(OrderMapper.toRestaurant(createOrderRequest)))
        .thenReturn(Optional.of(restaurantResponse));
    OrderDomainException orderDomainException =
        assertThrows(
            OrderDomainException.class,
            () -> orderApplicationService.createOrder(createOrderRequest));
    assertEquals(
        "Restaurant with id: " + RESTAURANT_ID + " is currently not active",
        orderDomainException.getMessage());
  }
}
