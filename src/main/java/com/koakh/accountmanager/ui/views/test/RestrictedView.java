package com.koakh.accountmanager.ui.views.test;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.koakh.accountmanager.MainUI;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 09/12/2016.
 */
@SpringView(name = RestrictedView.VIEW_NAME, ui={ MainUI.class })
public class RestrictedView extends VerticalLayout implements View {
  public static final String VIEW_NAME = "restricted";

  @PostConstruct
  void init() {
    setMargin(true);
    setSpacing(true);
    addComponent(new Label("This is a restricted view"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}
