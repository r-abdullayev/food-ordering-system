package com.ram.food.ordering.system.order.service.domain.dto.create;

import com.ram.food.ordering.system.domain.value.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateOrderResponse(
    @NotNull UUID orderTrackingId, @NotNull OrderStatus orderStatus, @NotNull String message) {}
