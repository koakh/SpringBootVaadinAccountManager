package com.koakh.accountmanager.ui.forms;

import com.vaadin.ui.*;

/**
 * Created by mario on 07/12/2016.
 * Sub-Window Management
 * https://vaadin.com/docs/-/part/framework/layout/layout-sub-window.html
 */
public class CustomerForm extends Window {
  public CustomerForm() {
    super("Subs on Sale"); // Set window caption
    center();

    // Some basic content for the window
    VerticalLayout content = new VerticalLayout();
    content.addComponent(new Label("Just say it's OK!"));
    content.setMargin(true);
    setContent(content);

    // Disable the close button
    setClosable(false);

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
