package com.koakh.springbootvaadinaccountmanager.ui.views.test;

import com.koakh.springbootvaadinaccountmanager.Greeter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 09/12/2016.
 */
@SpringView(name = ViewScopedView.VIEW_NAME)
public class ViewScopedView extends VerticalLayout implements View {
  public static final String VIEW_NAME = "view";

  // The same instance will be used by all views of the UI
  // noted by always the same id object com.example.Greeter@76d435f9
  @Autowired
  private Greeter uiGreeter;

  // A new instance will be created for every view instance created
  // It has the @ViewScope
  // noted by always diferent id object on refresh, com.example.ViewGreeter@1e28bd8d...com.example.ViewGreeter@5b765e98...etc
  @Autowired
  private ViewGreeter viewGreeter;

  @PostConstruct
  void init() {
    setMargin(true);
    setSpacing(true);
    addComponent(new Label("This is a view scoped view"));

    // Now when switching views, the UI scoped greeter instance and the UI scoped view will stay the same,
    // whereas the view scoped greeter (and the view itself) will be regenerated every time when entering the view.
    addComponent(new Label(uiGreeter.sayHello()));
    addComponent(new Label(viewGreeter.sayHello()));
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }
}
