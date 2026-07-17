package com.ram.food.ordering.system.domain.value;

import java.util.UUID;

public record CustomerId(UUID value) implements Identifier<UUID> {}
