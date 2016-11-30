package hello.model.customer;

import hello.Application;
import io.bloco.faker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerFaker {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void fakeDate(CustomerRepository repository, Long noOfRecords) {

    try {
      Faker faker = new Faker();

      for (int i = 0; i < noOfRecords; i++) {

        Customer customer = new Customer(
            faker.name.firstName(),
            faker.name.lastName(),
            faker.date.birthday(),
            faker.internet.email()
        );

        //log.info(String.format("Creating: %s", customer.toString()));

        repository.save(customer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

