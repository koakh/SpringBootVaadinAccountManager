package com.koakh.springbootvaadinaccountmanager.ui.views.system;

import com.koakh.springbootvaadinaccountmanager.Application;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Created by mario on 07/12/2016.
 */

@SpringUI
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

  enum Gender {
    MALE, FEMALE
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

/*
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
*/


/*
    Grid grid = new Grid();
    grid.setSizeFull();

    // init filter this add's a HeaderRow to the given grid
    final GridCellFilter filter = new GridCellFilter(grid);
    filter.setNumberFilter("id");

    // set gender Combo with custom icons
    ComboBox genderCombo = filter.setComboBoxFilter("gender", Arrays.asList(Gender.MALE, Gender.FEMALE));
    genderCombo.setItemIcon(Gender.MALE, FontAwesome.MALE);
    genderCombo.setItemIcon(Gender.FEMALE, FontAwesome.FEMALE);

    // simple filters
    filter.setTextFilter("name", true, true);
    filter.setNumberFilter("bodySize");
    filter.setDateFilter("birthday");
    filter.setBooleanFilter("onFacebook");
*/

    // Create a grid
    Grid grid = new Grid();
    grid.setSizeFull();
    grid.setCaption("My Grid");

    // Define some columns
    grid.addColumn("id", Integer.class);
    grid.addColumn("name", String.class);
    grid.addColumn("born", Integer.class);

    // Add some data rows
    grid.addRow(1, "Nicolaus Copernicus", 1543);
    grid.addRow(2, "Galileo Galilei", 1564);
    grid.addRow(3, "Johannes Kepler", 1571);

    grid.getColumn("id").setHeaderCaption("##");

//grid.getColumn("id").setRenderer(new EditDeleteButtonValueRenderer(new EditDeleteButtonValueRenderer.EditDeleteButtonClickListener() {
//
//  @Override
//  public void onEdit(final ClickableRenderer.RendererClickEvent event) {
//    Notification.show(event.getItemId().toString() + " want's to get edited", Notification.Type.HUMANIZED_MESSAGE);
//  }
//
//  @Override
//  public void onDelete(final com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent event) {
//    Notification.show(event.getItemId().toString() + " want's to get delete", Notification.Type.WARNING_MESSAGE);
//  }
//}));

    // first of all you need to set a custom style to the column
    grid.setCellStyleGenerator(new Grid.CellStyleGenerator() {
      @Override
      public String getStyle(final Grid.CellReference cellReference) {
        if (cellReference.getPropertyId()
            .equals("id")) {
          return "link-icon";
        } else {
          return null;
        }
      }
    });

    return grid;
  }
}
