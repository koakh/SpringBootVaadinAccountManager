package hello;

import hello.model.country.CountryFaker;
import hello.model.country.CountryRepository;
import hello.model.customer.CustomerFaker;
import hello.model.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  //Inject Configuration Properties
  @Value("${spring.datasource.url}")
  private String datasourceUrl;
  @Value("${model.faker.records.customer}")
  private long  apFakerRecordsCustomer;
  @Value("${model.faker.records.country}")
  private long  apFakerRecordsCountry;

  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @Bean
  public CommandLineRunner loadData(CustomerRepository customerRepository, CountryRepository countryRepository) {
    return (args) -> {

      //Show DatasourceUrl
      log.info(String.format("datasourceUrl: %s", datasourceUrl));

      //Mock Data
      if (countryRepository.count() == 0) {
        CountryFaker.fakeDate(countryRepository, apFakerRecordsCountry);
      }
      if (customerRepository.count() == 0) {
        CustomerFaker.fakeDate(customerRepository, countryRepository, apFakerRecordsCustomer);
      }
    };
  }
}
