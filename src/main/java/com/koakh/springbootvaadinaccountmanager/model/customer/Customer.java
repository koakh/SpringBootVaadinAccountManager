package com.koakh.springbootvaadinaccountmanager.model.customer;

import com.koakh.springbootvaadinaccountmanager.model.BaseEntity;
import com.koakh.springbootvaadinaccountmanager.model.country.Country;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer /*extends BaseEntity*/ {

  @Id
  @GeneratedValue
  private Long id;
  @NaturalId
  private UUID uuid = UUID.randomUUID();
  @NotNull
  @Size(min=2, max=30)
  private String firstName;
  @NotNull
  @Size(min=2, max=30)
  private String lastName;
  @NotNull
  private Date bornIn;
  @NotNull
  @Pattern(regexp="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
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