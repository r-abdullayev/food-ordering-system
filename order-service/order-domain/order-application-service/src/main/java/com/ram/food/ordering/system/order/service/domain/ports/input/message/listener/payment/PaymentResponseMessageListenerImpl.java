package com.ram.food.ordering.system.order.service.domain.ports.input.message.listener.payment;

import com.ram.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Component
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

  @Override
  public void paymentCompleted(PaymentResponse paymentResponse) {}

  @Override
  public void paymentCancelled(PaymentResponse paymentResponse) {}
}
