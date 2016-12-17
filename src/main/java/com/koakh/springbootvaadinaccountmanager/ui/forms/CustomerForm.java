package com.koakh.springbootvaadinaccountmanager.ui.forms;

import com.koakh.springbootvaadinaccountmanager.model.customer.Customer;
import com.vaadin.ui.*;

/**
 * Created by mario on 07/12/2016.
 * Sub-Window Management
 * https://vaadin.com/docs/-/part/framework/layout/layout-sub-window.html
 */
public class CustomerForm extends BaseForm {

  public CustomerForm(Customer customer) {
    super("Edit Customer", 600.0f, 400.0f); // Set window caption

    verticalLayout.addComponent(new Label(customer.getFirstName()));
  }
}
