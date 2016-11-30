package hello.model.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer {

  @Id
  @GeneratedValue
  private Long id;
  @NotNull
  private String firstName;
  @NotNull
  private String lastName;
  @NotNull
  private Date bornIn;
  @NotNull
  private String email;
  //@NotNull
  //private Country country;

  protected Customer() {
  }

  public Customer(String firstName, String lastName, Date bornIn, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.bornIn = bornIn;
    this.email = email;
  }

  public Long getId() {
    return id;
  }

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

  //public Country getCountry() {
  //  return country;
  //}

  //public void setCountry(Country country) {
  //  this.country = country;
  //}

  @Override
  public String toString() {
    return String.format("Customer[id=%d, firstName='%s', lastName='%s', bornIn='%s', email='%s']",
        id, firstName, lastName, bornIn, email);
  }
}