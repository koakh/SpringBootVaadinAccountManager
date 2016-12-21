package com.koakh.springbootvaadinaccountmanager.ui.views.test;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.access.ViewAccessControl;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 09/12/2016.
 */

// implements ViewAccessControl

//Required @UIScope: Else Error creating bean with name 'protectedView': Scope 'vaadin-view' is not active for the current thread; consider defining a scoped proxy for this bean if you intend to refer to it from a singleton

@UIScope
@SpringView(name = ProtectedView.VIEW_NAME)
public class ProtectedView extends VerticalLayout implements View, ViewAccessControl {

  public static final String VIEW_NAME = "protected";

  @PostConstruct
  void init() {
    setMargin(true);
    setSpacing(true);
    addComponent(new Label("This is the a protected access view"));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }

  @Override
  public boolean isAccessGranted(UI ui, String s) {

    // Require to be true, else we are always sent to Error View!!
    // Any Spring bean implementing ViewAccessControl is first asked if access is granted to a view with the given bean name

    //Protect this View
    boolean granted = (! s.equals("protectedView"));

    return granted;
  }
}