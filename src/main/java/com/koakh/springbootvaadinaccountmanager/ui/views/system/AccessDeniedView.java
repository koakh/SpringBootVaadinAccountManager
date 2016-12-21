package com.koakh.springbootvaadinaccountmanager.ui.views.system;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 09/12/2016.
 */

//Required @UIScope: Else Error Error creating bean with name 'accessDenied': Scope 'vaadin-view' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton

@UIScope
@SpringView(name = AccessDeniedView.VIEW_NAME)
public class AccessDeniedView extends VerticalLayout implements View {

  public static final String VIEW_NAME = "accessdenied";

  @PostConstruct
  void init() {
    setMargin(true);
    setSpacing(true);
    addComponent(new Label("This is the default access denied view"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}
