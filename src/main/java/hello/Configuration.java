package hello;

import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.context.annotation.Bean;

/**
 * Created by mario on 07/12/2016.
 */

// Annotation based configuration
// The recommended configuration option with Vaadin Spring is to use annotation based configuration (AnnotationConfigWebApplicationContext)
// and configuration classes annotated with @Configuration, where beans can be explicitly declared with @Bean on a method.
// When using this approach, simply add the annotation @EnableVaadin for one of your configuration classes to automatically import VaadinConfiguration.
@org.springframework.context.annotation.Configuration
// this imports VaadinConfiguration
@EnableVaadin
public class Configuration {

  // application specific configuration - register myBean in the context
  @Bean
  public MyBean myBean() {
    return new MyBean();
  }
}
