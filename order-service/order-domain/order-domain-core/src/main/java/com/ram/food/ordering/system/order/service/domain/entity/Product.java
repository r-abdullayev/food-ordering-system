package com.ram.food.ordering.system.order.service.domain.entity;

import com.ram.food.ordering.system.domain.entity.BaseEntity;
import com.ram.food.ordering.system.domain.value.Money;
import com.ram.food.ordering.system.domain.value.ProductId;

public class Product extends BaseEntity<ProductId> {
  private String name;
  private Money price;

  public Product(ProductId id, String name, Money price) {
    super.setId(id);
    this.name = name;
    this.price = price;
  }

  public Product(ProductId productId) {
    super.setId(productId);
  }

  public void updateWithConfirmedNameAndPrice(String name, Money price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public Money getPrice() {
    return price;
  }
}
