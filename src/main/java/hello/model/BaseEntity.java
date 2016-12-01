package hello.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by mario on 01/12/2016.
 */
public abstract class BaseEntity implements Serializable {

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
