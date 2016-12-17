package com.koakh.springbootvaadinaccountmanager;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

/**
 * Created by mario on 07/12/2016.
 */
@SpringComponent
@UIScope
public class Greeter {
  public String sayHello() {

    return "Hello from bean " + toString();
  }
}
