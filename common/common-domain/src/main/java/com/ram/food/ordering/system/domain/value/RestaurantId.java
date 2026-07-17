package com.ram.food.ordering.system.domain.value;

import java.util.UUID;

public record RestaurantId(UUID value) implements Identifier<UUID> {}
