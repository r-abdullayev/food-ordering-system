package com.ram.food.ordering.system.order.service.domain.value;

import com.ram.food.ordering.system.domain.value.Identifier;
import java.util.UUID;

public record TrackingId(UUID value) implements Identifier<UUID> {}
