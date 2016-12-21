package com.koakh.springbootvaadinaccountmanager.ui.forms;

import com.koakh.springbootvaadinaccountmanager.model.country.CountryRepository;
import com.koakh.springbootvaadinaccountmanager.model.customer.Customer;
import com.koakh.springbootvaadinaccountmanager.model.customer.CustomerRepository;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mario on 07/12/2016.
 * Sub-Window Management
 * https://vaadin.com/docs/-/part/framework/layout/layout-sub-window.html
 */

@SpringComponent
@UIScope
public class CustomerForm extends BaseForm {

  @Autowired
  private final CustomerRepository customerRepository;
  @Autowired
  private final CountryRepository countryRepository;

  //private final Customer customer;

  //public CustomerForm(Customer customer) {
  public CustomerForm(CustomerRepository customerRepository, CountryRepository countryRepository) {

    super("Edit Customer", 600.0f, 400.0f); // Set window caption

    //this.customer = customer;
    this.customerRepository = customerRepository;
    this.countryRepository = countryRepository;

    init();
  }

  private void init() {

    Button save = new Button("Save");
    save.addClickListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        customerRepository.findAll();
      }
    });

    contentPanel.setContent(save);
    //contentPanel.setContent(new Label(customer.getFirstName()));
  }
}
