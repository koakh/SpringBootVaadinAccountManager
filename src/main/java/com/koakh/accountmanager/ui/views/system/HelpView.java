package com.koakh.accountmanager.ui.views.system;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.koakh.accountmanager.Application;
import com.koakh.accountmanager.ui.views.IViewNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 07/12/2016.
 */
@SpringView(name = IViewNames.HELP)
public class HelpView extends VerticalLayout implements View {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  public HelpView() {
    setSizeFull();

    Button button = new Button("Go to Help View",
        new Button.ClickListener() {
          @Override
          public void buttonClick(Button.ClickEvent event) {
//navigator.navigateTo(VaadinUI.MAINVIEW);
          }
        });
    addComponent(button);
    setComponentAlignment(button, Alignment.MIDDLE_CENTER);
  }

  @PostConstruct
  void init() {

    Notification.show("Init: Welcome to Help View");
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

    Notification.show("Enter: Welcome to Help View");
  }
}