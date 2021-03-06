package com.koakh.springbootvaadinaccountmanager.ui.forms;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by mario on 17/12/2016.
 * Sub-Window Management
 * https://vaadin.com/docs/-/part/framework/layout/layout-sub-window.html
 */
public abstract class BaseForm extends Window {

  protected Panel contentPanel;
  private boolean showNotification = false;

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

    contentPanel = new Panel();

    Button save = new Button("Save");
    save.addClickListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        saveAction();
        if (showNotification) Notification.show("Record Saved", Notification.Type.HUMANIZED_MESSAGE);
        close();
      }
    });

    Button delete = new Button("Delete");
    delete.addClickListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        deleteAction();
        if (showNotification) Notification.show("Record Deleted", Notification.Type.WARNING_MESSAGE);
        close();
      }
    });

    Button close = new Button("Close");
    close.addClickListener(new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        close();
      }
    });

    // ShortCuts
    save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    close.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

    // Box Filter and Clear Filter inside CssLayout, usefull in this case to group components in one component
    CssLayout cssLayoutButtons = new CssLayout();
    cssLayoutButtons.addComponents(save, close);
    cssLayoutButtons.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    cssLayoutButtons.setSizeUndefined();
    cssLayoutButtons.addComponent(save);
    cssLayoutButtons.addComponent(delete);
    cssLayoutButtons.addComponent(close);

    // Add HorizontalLayout to House cssLayoutButtons Aligned Right
    HorizontalLayout horizontalLayout = new HorizontalLayout();
    horizontalLayout.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT);
    horizontalLayout.setWidth(100.0f, Unit.PERCENTAGE);
    horizontalLayout.addComponent(cssLayoutButtons);

    // Add Vertical Layout to House Panel and Buttons
    VerticalLayout verticalLayout = new VerticalLayout();
    verticalLayout.setMargin(true);
    verticalLayout.setSpacing(true);
    verticalLayout.addComponent(contentPanel);
    verticalLayout.addComponent(horizontalLayout);

    // Finally set Content
    setContent(verticalLayout);
  }

  // Must be Overridden in child
  protected void deleteAction() {
  }

  // Must be Overridden in child
  protected void saveAction() {
  }

  // Must be Overridden in child
  protected void closeAction() {
  }
}