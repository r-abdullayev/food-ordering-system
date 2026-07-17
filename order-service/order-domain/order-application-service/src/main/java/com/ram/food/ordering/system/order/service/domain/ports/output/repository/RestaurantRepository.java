package com.ram.food.ordering.system.order.service.domain.ports.output.repository;

import com.ram.food.ordering.system.order.service.domain.entity.Restaurant;
import java.util.Optional;

public interface RestaurantRepository {

  Optional<Restaurant> findRestaurant(Restaurant restaurant);
}
