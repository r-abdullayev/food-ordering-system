package com.ram.food.ordering.system.order.service.domain.dto.message;

import com.ram.food.ordering.system.domain.value.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.Builder;

@Builder
public record PaymentResponse(
    String id,
    String sagaId,
    String orderId,
    String paymentId,
    String customerId,
    BigDecimal price,
    Instant createdAt,
    PaymentStatus paymentStatus,
    List<String> failureMessages) {}
