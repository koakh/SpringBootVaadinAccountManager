package hello.model.country;

import hello.Application;
import io.bloco.faker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountryFaker {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public static void fakeDate(CountryRepository repository, Long noOfRecords) {
    try {
      Faker faker = new Faker();

      for (int i = 0; i < noOfRecords; i++) {

        Country country = new Country(
            faker.address.country(),
            faker.address.countryCode()
        );

        log.info(String.format("Creating: %s", country.toString()));

        repository.save(country);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
