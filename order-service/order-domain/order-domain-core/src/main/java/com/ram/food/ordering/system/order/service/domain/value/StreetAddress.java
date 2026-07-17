package com.ram.food.ordering.system.order.service.domain.value;

import java.util.UUID;

public record StreetAddress(UUID id, String street, String postalCode, String city) {}
