package hello.model.customer;

import hello.model.country.Country;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

  @Id
  @GeneratedValue
  //@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NaturalId
  UUID uuid = UUID.randomUUID();
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

  // Required parametless constructor
  protected Customer() { }

  public Customer(String firstName, String lastName, Date bornIn, String email, Country country) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.bornIn = bornIn;
    this.email = email;
    this.country = country;
  }

  //Override ToString
  @Override
  public String toString() {
    return String.format("Customer[id=%d, firstName='%s', lastName='%s', bornIn='%s', email='%s']",
        id, firstName, lastName, bornIn, email);
  }

  //Business key equality : Implementing equals() and hashCode()
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Customer)) return false;
    Customer book = (Customer) o;
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
}