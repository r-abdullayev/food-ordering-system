package com.ram.food.ordering.system.order.service.domain.value;

import com.ram.food.ordering.system.domain.value.Identifier;

public record OrderItemId(Long value) implements Identifier<Long> {}
