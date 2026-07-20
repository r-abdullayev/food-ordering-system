package com.ram.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval;

import com.ram.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
public class RestaurantApprovalResponseMessageListenerImpl
    implements RestaurantApprovalResponseMessageListener {

  @Override
  public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {}

  @Override
  public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {}
}
