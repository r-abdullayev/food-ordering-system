package com.ram.food.ordering.system.order.service.domain;

import static org.mockito.Mockito.mock;

import com.ram.food.ordering.system.order.service.domain.ports.output.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.ram.food.ordering.system.order.service.domain.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.ram.food.ordering.system.order.service.domain.ports.output.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.ram.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import com.ram.food.ordering.system.order.service.domain.service.OrderDomainService;
import com.ram.food.ordering.system.order.service.domain.service.impl.OrderDomainServiceImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.ram.food.ordering.system")
public class OrderTestConfiguration {

  @Bean
  public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
    return mock(OrderCreatedPaymentRequestMessagePublisher.class);
  }

  @Bean
  public OrderCancelledPaymentRequestMessagePublisher
      orderCancelledPaymentRequestMessagePublisher() {
    return mock(OrderCancelledPaymentRequestMessagePublisher.class);
  }

  @Bean
  public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
    return mock(OrderPaidRestaurantRequestMessagePublisher.class);
  }

  @Bean
  public OrderRepository orderRepository() {
    return mock(OrderRepository.class);
  }

  @Bean
  public CustomerRepository customerRepository() {
    return mock(CustomerRepository.class);
  }

  @Bean
  public RestaurantRepository restaurantRepository() {
    return mock(RestaurantRepository.class);
  }

  @Bean
  public OrderDomainService orderDomainService() {
    return new OrderDomainServiceImpl();
  }
}
