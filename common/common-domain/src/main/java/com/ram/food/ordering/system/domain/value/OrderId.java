package com.ram.food.ordering.system.domain.value;

import java.util.UUID;

public record OrderId(UUID value) implements Identifier<UUID> {}
