package com.ram.food.ordering.system.domain.entity;

import com.ram.food.ordering.system.domain.value.Identifier;
import java.util.Objects;

public abstract class BaseEntity<ID extends Identifier<?>> {
  private ID id;

  public void setId(ID id) {
    this.id = id;
  }

  public ID getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BaseEntity<?> that = (BaseEntity<?>) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
