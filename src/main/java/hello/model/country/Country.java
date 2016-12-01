package hello.model.country;

import hello.model.customer.Customer;
import org.hibernate.annotations.NaturalId;
import org.hibernate.id.GUIDGenerator;
import org.yaml.snakeyaml.events.Event;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "country")
public class Country implements Serializable {

  @Id
  @GeneratedValue
  Long id;
  @NaturalId
  UUID uuid = UUID.randomUUID();
  @NotNull
  String name;
  @NotNull
  String code2;
  @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Customer> Customers;

  // Required parametless constructor
  protected Country() { }

  public Country(String name, String code2) {
    this.name = name;
    this.code2 = code2;
  }

  //Business key equality : Implementing equals() and hashCode()
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Country)) return false;
    Country book = (Country) o;
    return Objects.equals(getId(), book.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  public Long getId() {
    return id;
  }

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
}