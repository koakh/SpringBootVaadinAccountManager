package com.koakh.accountmanager.model;

/**
 * Created by mario on 09/12/2016.
 */
public class BaseEntity {
  /*
  @Id
  @GeneratedValue
  protected Long id;
  @NaturalId
  protected UUID uuid = UUID.randomUUID();

  //Business key equality : Implementing equals() and hashCode()
  // Very important, ex remeber ComboBox problem...
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof  BaseEntity)) return false;
    BaseEntity baseEntity = (BaseEntity) o;
    return Objects.equals(getId(), baseEntity.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }
  */
}
