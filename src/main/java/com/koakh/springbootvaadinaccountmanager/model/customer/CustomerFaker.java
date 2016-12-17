package com.koakh.springbootvaadinaccountmanager.model.customer;

import com.koakh.springbootvaadinaccountmanager.Application;
import com.koakh.springbootvaadinaccountmanager.model.country.CountryRepository;
import io.bloco.faker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerFaker {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void fakeDate(CustomerRepository customerRepository, CountryRepository countryRepository, Long noOfRecords) {

    try {
      Faker faker = new Faker();

      for (int i = 0; i < noOfRecords; i++) {

        Customer customer = new Customer(
            faker.name.firstName(),
            faker.name.lastName(),
            faker.date.birthday(),
            faker.internet.email(),
            countryRepository.findOne(Integer.toUnsignedLong(faker.number.positive(1, 100)))
        );

        //log.info(String.format("Creating: %s", customer.toString()));

        customerRepository.save(customer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

