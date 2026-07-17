package com.ram.food.ordering.system.order.service.domain.dto.track;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record TrackOrderRequest(@NotNull UUID orderTrackingId) {}
