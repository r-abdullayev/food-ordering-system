package com.ram.food.ordering.system.order.service.domain.dto.create;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateOrderRequest(
    @NotNull UUID customerId,
    @NotNull UUID restaurantId,
    @NotNull BigDecimal price,
    @NotNull List<OrderItem> orderItems,
    @NotNull OrderAddress address) {}
