package hello.model.country;

import hello.model.BaseEntity;
import hello.model.customer.Customer;
import org.hibernate.annotations.NaturalId;
import org.hibernate.id.GUIDGenerator;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "country")
public class Country extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;
  @NaturalId
  private UUID uuid = UUID.randomUUID();
  @NotNull
  private String name;
  @NotNull
  private String code2;
  @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<Customer> Customers;

  // Required parametless constructor
  protected Country() { }

  public Country(String name, String code2) {
    this.name = name;
    this.code2 = code2;
  }

  // Getters and Setters
  public Long getId() { return id; }

  public UUID getUuid() { return uuid; }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode2() {
    return code2;
  }

  public void setCode2(String code2) {
    this.code2 = code2;
  }

  // Helper Methods
  public boolean isPersisted() {
    return id != null;
  }

  //Override ToString
  @Override
  public String toString() {
    return String.format("Country[id=%d, uuid='%s', name='%s', code2='%s']",
        id, uuid, name, code2);
  }

  //Business key equality : Implementing equals() and hashCode()
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (this.id == null) {
      return false;
    }

    if (obj instanceof Country && obj.getClass().equals(getClass())) {
      return this.id.equals(((Country) obj).id);
    }

    return false;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 43 * hash + (id == null ? 0 : id.hashCode());
    return hash;
  }

  @Override
  public Customer clone() throws CloneNotSupportedException {
    return (Customer) super.clone();
  }
}