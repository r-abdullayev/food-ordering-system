package com.ram.food.ordering.system.order.service.domain.dto.track;

import com.ram.food.ordering.system.domain.value.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TrackOrderResponse(
    @NotNull UUID orderTrackingId,
    @NotNull OrderStatus orderStatus,
    List<String> failureMessages) {}
