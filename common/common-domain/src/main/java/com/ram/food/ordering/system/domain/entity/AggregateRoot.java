package com.ram.food.ordering.system.domain.entity;

import com.ram.food.ordering.system.domain.value.Identifier;

public abstract class AggregateRoot<ID extends Identifier<?>> extends BaseEntity<ID> {}
