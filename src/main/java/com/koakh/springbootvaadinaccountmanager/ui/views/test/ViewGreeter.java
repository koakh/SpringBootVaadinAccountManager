package com.koakh.springbootvaadinaccountmanager.ui.views.test;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

/**
 * Created by mario on 09/12/2016.
 */

// Note the annotation @ViewScope, which makes the lifecycle and injection of instances of this bean view specific.
// With @ViewScope, always is created a fresh new instance, one instance per ViewScope

@SpringComponent
@ViewScope
public class ViewGreeter {
  public String sayHello() {
    return "Hello from a view scoped bean " + toString();
  }
}
