package com.koakh.springbootvaadinaccountmanager.ui.views.test;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 09/12/2016.
 * This view is registered automatically based on the @SpringView annotation.
 * As it has an empty string as its view name, it will be shown when navigating to http://localhost:8080/
 */
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
  public static final String VIEW_NAME = "";

  @PostConstruct
  void init() {
    setMargin(true);
    setSpacing(true);
    addComponent(new Label("This is the default view"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}