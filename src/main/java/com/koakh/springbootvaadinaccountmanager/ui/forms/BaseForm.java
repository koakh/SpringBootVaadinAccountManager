package com.koakh.springbootvaadinaccountmanager.ui.forms;

import com.koakh.springbootvaadinaccountmanager.model.customer.Customer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * Created by mario on 17/12/2016.
 */
public class BaseForm extends Window {

  protected VerticalLayout verticalLayout;

  public BaseForm(String title, float width, float height) {

    // Child Properties
    super(title); // Set window caption
    setWidth(width, Unit.POINTS);
    setHeight(height, Unit.POINTS);

    //Properties
    center();
    setModal(true);
    setClosable(false);
    setResizable(false);

    // Some basic content for the window
    verticalLayout = new VerticalLayout();
    verticalLayout.setMargin(true);
    setContent(verticalLayout);

    // Trivial logic for closing the sub-window
    Button ok = new Button("OK");
    ok.addClickListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        close(); // Close the sub-window
      }
    });

    verticalLayout.addComponent(ok);
  }
}