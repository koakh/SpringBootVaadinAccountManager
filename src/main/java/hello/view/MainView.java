package hello.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import hello.ui.VaadinUI;

/**
 * Created by mario on 07/12/2016.
 */
public class MainView extends VerticalLayout implements View {
  public MainView(Navigator navigator) {
    setSizeFull();

    Button button = new Button("Go to Main View",
        new Button.ClickListener() {
          @Override
          public void buttonClick(Button.ClickEvent event) {
            navigator.navigateTo("");
          }
        });
    addComponent(button);
    setComponentAlignment(button, Alignment.MIDDLE_CENTER);
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    Notification.show("Welcome to Main View");
  }
}