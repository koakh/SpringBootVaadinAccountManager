package hello.model.customer;

import hello.model.BaseEntity;
import hello.model.country.Country;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;
  @NaturalId
  private UUID uuid = UUID.randomUUID();
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private Date bornIn;
  @NotNull
  private String email;
  @NotNull
  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;
  @NotNull
  private CustomerStatus status;

  // Required parametless constructor
  protected Customer() { }

  public Customer(String firstName, String lastName, Date bornIn, String email, Country country) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.bornIn = bornIn;
    this.email = email;
    this.country = country;
  }

  // Getter and Setters
  public Long getId() { return id; }

  public UUID getUuid() { return uuid; }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getBornIn() {
    return bornIn;
  }

  public void setBornIn(Date bornIn) {
    this.bornIn = bornIn;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public CustomerStatus getStatus() { return status; }

  public void setStatus(CustomerStatus status) { this.status = status; }

  // Helper Methods
  public boolean isPersisted() {
    return id != null;
  }

  //Override ToString
  @Override
  public String toString() {
    return String.format("Customer[id=%d, uuid='%s', firstName='%s', lastName='%s', bornIn='%s', email='%s']",
        id, uuid, firstName, lastName, bornIn, email);
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

    if (obj instanceof Customer && obj.getClass().equals(getClass())) {
      return this.id.equals(((Customer) obj).id);
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