package com.koakh.accountmanager.ui.views.system;

import com.fasterxml.jackson.annotation.JsonValue;
import com.koakh.accountmanager.ui.renderers.BoldLastNameRenderer;
import com.koakh.accountmanager.ui.renderers.FontIconRenderer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.koakh.accountmanager.Application;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Random;

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
    addComponent(getGrid());
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()
  }

  //Column Renderes
  //http://demo.vaadin.com/sampler/#ui/grids-and-trees/grid/renderers
  //https://github.com/Koziolek/FontAwesomeRenderer/blob/master/FontAwesomeRenderer-demo/src/main/java/pl/koziolekweb/demo/DemoUI.java
  private Grid getGrid() {

/*
    Grid grid = new Grid();
    grid.setSizeFull();

    grid.addColumn("index", Integer.class)
        .setRenderer(new NumberRenderer("%02d")).setHeaderCaption("##")
        .setExpandRatio(0);

    grid.addColumn("name", String.class)
        .setRenderer(new BoldLastNameRenderer()).setExpandRatio(2);

    grid.addColumn("progress", Double.class)
        .setRenderer(new ProgressBarRenderer()).setExpandRatio(2);

    grid.addColumn("edit", FontIcon.class)
        .setRenderer(new FontIconRenderer(new ClickableRenderer.RendererClickListener() {
          @Override
          public void click(ClickableRenderer.RendererClickEvent e) {
            Notification.show("Editing item " + e.getItemId());
          }
        }));

    grid.addColumn("delete", FontIcon.class)
        .setRenderer(new FontIconRenderer(new ClickableRenderer.RendererClickListener() {
          @Override
          public void click(ClickableRenderer.RendererClickEvent e) {
            Notification.show("Deleted item " + e.getItemId());
          }
        }));

    grid.getDefaultHeaderRow().join("edit", "delete").setText("Tools");

    return grid;
*/


    Grid grid = new Grid();

    grid.addColumn("name", String.class).setHeaderCaption("Name");
    grid.addColumn("icon", String.class)
        .setHeaderCaption("icon")
        .setRenderer(new FontIconRenderer(e -> Notification.show(e.toString())));

    grid.getDefaultHeaderRow().join("name", "icon").setText("Brand");

    grid.addRow("Android", FontAwesome.ANDROID.getHtml());
    grid.addRow("Ios", FontAwesome.APPLE.getHtml());
    grid.addRow("Who cares", FontAwesome.WINDOWS.getHtml());

    return grid;

  }
}


