package com.koakh.accountmanager.ui.views.system;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.koakh.accountmanager.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 07/12/2016.
 */

@SpringView(name = HelpView.VIEW_NAME)
public class HelpView extends VerticalLayout implements View {
  public static final String VIEW_NAME = "help";

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  @PostConstruct
  void init() {
    setMargin(true);
    setSpacing(true);
    addComponent(new Label("This is the default help view"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}