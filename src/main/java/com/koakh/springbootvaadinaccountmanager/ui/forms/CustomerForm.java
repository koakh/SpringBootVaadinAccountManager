package com.koakh.springbootvaadinaccountmanager.ui.forms;

import com.koakh.springbootvaadinaccountmanager.model.customer.Customer;
import com.vaadin.ui.*;

/**
 * Created by mario on 07/12/2016.
 * Sub-Window Management
 * https://vaadin.com/docs/-/part/framework/layout/layout-sub-window.html
 */
public class CustomerForm extends Window {

  private float width = 600.0f;
  private float height = 400.0f;

  public CustomerForm(Customer customer) {
    super("Edit Customer"); // Set window caption
    center();
    setModal(true);
    setClosable(false);
    setWidth(width, Unit.POINTS);
    setHeight(height, Unit.POINTS);

    // Some basic content for the window
    VerticalLayout content = new VerticalLayout();
    content.addComponent(new Label("Just say it's OK!"));
    content.setMargin(true);
    setContent(content);

    // Trivial logic for closing the sub-window
    Button ok = new Button("OK");
    ok.addClickListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        close(); // Close the sub-window
      }
    });
    content.addComponent(ok);
  }
}
