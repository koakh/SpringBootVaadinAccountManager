package com.koakh.springbootvaadinaccountmanager.model.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
//@EnableJpaRepositories
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  List<Customer> findByLastNameStartsWithIgnoreCase(String lastName);
}