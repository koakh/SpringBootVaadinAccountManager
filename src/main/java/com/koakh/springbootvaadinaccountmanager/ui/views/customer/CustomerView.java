package com.koakh.springbootvaadinaccountmanager.ui.views.customer;

import com.koakh.springbootvaadinaccountmanager.Application;
import com.koakh.springbootvaadinaccountmanager.model.country.Country;
import com.koakh.springbootvaadinaccountmanager.model.customer.Customer;
import com.koakh.springbootvaadinaccountmanager.model.customer.CustomerRepository;
import com.koakh.springbootvaadinaccountmanager.ui.forms.CustomerForm;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.vaadin.gridutil.renderer.EditButtonValueRenderer;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * Created by mario on 07/12/2016.
 */
//@SpringView(name = IViewNames.CUSTOMER)
@SpringView(name = CustomerView.VIEW_NAME)
public class CustomerView extends VerticalLayout implements View {

  public static final String VIEW_NAME = "customer";

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  // UI Components
  private Grid grid;
  private TextField textFieldFilter;
  // Actions
  private Button buttonClearFilter;
  private Button buttonNewRecord;
  private Button buttonRefresh;
  private Button buttonsPopup;
  // Defaults
  private Country countryDefault;

  @Value("${model.faker.records.country.default}")
  private long countryDefaultId;

  // Reference to Constructor Autowired Beans
  private final CustomerRepository customerRepository;
  private final CustomerForm customerForm;

  @Autowired
  public CustomerView(CustomerRepository customerRepository, CustomerForm customerForm) {
    this.customerRepository = customerRepository;
    this.customerForm = customerForm;
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()

    Notification.show("Welcome to CustomerMain View");
  }

  @PostConstruct
  void init() {
    setSizeFull();
    //addStyleName(ValoTheme.PANEL_BORDERLESS);

    // Compose Toolbar UI Objects
    textFieldFilter = new TextField();
    buttonClearFilter = new Button("Clear filter", FontAwesome.TIMES);
    buttonNewRecord = new Button("New", FontAwesome.PLUS);
    buttonRefresh = new Button("Refresh", FontAwesome.REFRESH);
    // Assign placeHolder, used here for using method setInputPrompt only
    textFieldFilter.setInputPrompt("Filter by Last Name");

    // Box Filter and Clear Filter inside CssLayout, usefull in this case to group components in one component
    CssLayout cssLayoutFiltering = new CssLayout();
    cssLayoutFiltering.addComponents(textFieldFilter, buttonClearFilter);
    cssLayoutFiltering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

    // Build horizontalLayoutToolbar to pack Toolbar UI Objects
    HorizontalLayout horizontalLayoutToolbar = new HorizontalLayout(cssLayoutFiltering, buttonNewRecord, buttonRefresh);
    horizontalLayoutToolbar.setSpacing(true);

    // Start with a GeneratedPropertyContainer with all customers from repository, with custom properties(columns) added to model for action buttons
    GeneratedPropertyContainer generatedPropertyContainer = getGeneratedPropertyContainer(customerRepository.findAll());

    // Create a Grid, and assign GeneratedPropertyContainer same has SetContainerDataSource
    grid = new Grid(generatedPropertyContainer);
    grid.setSizeFull();
    // Reconfigure columns, Removing all from default model and add desires ones with actions buttons
    grid.removeAllColumns();
    // Model Columns
    grid.addColumn("id").setHeaderCaption("##").setWidth(100).setResizable(false).setRenderer(new NumberRenderer("%02d")).setExpandRatio(1);
    grid.addColumn("firstName").setHeaderCaption("First Name").setExpandRatio(2);
    grid.addColumn("lastName").setHeaderCaption("Last Name").setExpandRatio(2);
    //grid.addColumn("email").setHeaderCaption("Email").setExpandRatio(4);
    // Action Buttons
    grid.addColumn("edit").setHeaderCaption("Edit").setWidth(58).setResizable(false).setExpandRatio(1);//.setRenderer(new HtmlRenderer());
    grid.addColumn("delete").setHeaderCaption("Delete").setWidth(58).setResizable(false).setExpandRatio(1);//.setRenderer(new HtmlRenderer());
    // Configure columns : show all columns grid.getColumns(), and get grid.getColumn("columnName")
    // Join Action Columns
    grid.getDefaultHeaderRow().join("edit", "delete").setText("Actions");

    // Render a button that edits the data row (item)
//grid.getColumn("edit")
//.setRenderer(new ButtonRenderer(
//    e -> {
//      //Notification.show("Clicked " + grid.getContainerDataSource().getContainerProperty(e.getItemId(), "firstName"));
//      showPopup((Customer) e.getItemId());
//    })
//).setWidth(100).setResizable(false);

    // add custom button to active column
    grid.getColumn("edit").setRenderer(new EditButtonValueRenderer(
            new ClickableRenderer.RendererClickListener() {
              @Override
              public void click(final ClickableRenderer.RendererClickEvent event) {
                String recordId = grid.getContainerDataSource().getContainerProperty(event.getItemId(), "id").toString();
                Notification.show(String.format("Edit record: [#%s]", recordId), Notification.Type.WARNING_MESSAGE);
                showPopup((Customer) event.getItemId());
              }
            }
        )
    );

    //grid.getColumn("id")
    //    .setRenderer(new EditDeleteButtonValueRenderer(new EditDeleteButtonValueRenderer.EditDeleteButtonClickListener() {
    //
    //      @Override
    //      public void onEdit(final ClickableRenderer.RendererClickEvent event) {
    //        Notification.show(event.getItemId()
    //            .toString() + " want's to get edited", Notification.Type.HUMANIZED_MESSAGE);
    //      }
    //      @Override
    //      public void onDelete(final com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent event) {
    //        Notification.show(event.getItemId()
    //            .toString() + " want's to get delete", Notification.Type.WARNING_MESSAGE);
    //      };
    //
    //    }))
    //    .setWidth(200);

    // Render a button that deletes the data row (item)
//grid.getColumn("delete")
//    .setRenderer(new ButtonRenderer(e ->
//    {
//      //Notification.show("Clicked " + grid.getContainerDataSource().getContainerProperty(e.getItemId(), "firstName"));
//      Customer customer = (Customer) e.getItemId();
//      // Remove from grid
//      grid.getContainerDataSource().removeItem(customer);
//      //TODO : Required using getId()
//      //Remove from Repository
//      customerRepository.delete(customer.getId());
//    }
//    )).setWidth(100).setResizable(false);

    // add custom button to active column
    grid.getColumn("delete").setRenderer(new EditButtonValueRenderer(
            new ClickableRenderer.RendererClickListener() {
              @Override
              public void click(final ClickableRenderer.RendererClickEvent event) {
                String recordId = grid.getContainerDataSource().getContainerProperty(event.getItemId(), "id").toString();
                Notification.show(String.format("Delete record: [#%s]", recordId), Notification.Type.WARNING_MESSAGE);
                Customer customer = (Customer) event.getItemId();
                // Remove from grid
                grid.getContainerDataSource().removeItem(customer);
                //TODO : Required using getId()
                //Remove from Repository
                customerRepository.delete(customer.getId());
              }
            }
        )
    );

    // first of all you need to set a custom style to the column
    grid.setCellStyleGenerator(new Grid.CellStyleGenerator() {
      @Override
      public String getStyle(final Grid.CellReference cellReference) {
        if (cellReference.getPropertyId().equals("edit")) {
          return "link-icon-edit";
        } else if (cellReference.getPropertyId().equals("delete")) {
          return "link-icon-delete";
        } else {
          return null;
        }
      }
    });

    //grid.getColumn("delete").setRenderer(new FontIconRenderer(e -> Notification.show("Deleted item " + e.getItemId())));


    /*
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
    */

    // Build verticalLayoutMainContent
    VerticalLayout verticalLayoutMainContent = new VerticalLayout(horizontalLayoutToolbar, grid);
    verticalLayoutMainContent.setSizeFull();
    // Configure the grid to use all of the available space more efficiently.
    verticalLayoutMainContent.setExpandRatio(grid, 1);
    verticalLayoutMainContent.setMargin(true);
    verticalLayoutMainContent.setSpacing(true);

    // Add component to View
    addComponent(verticalLayoutMainContent);

    // Hook logic to components

    // Replace listing with filtered content when user changes filter
    textFieldFilter.addTextChangeListener(e -> listCustomers(e.getText()));

    buttonClearFilter.addClickListener(e -> {
      textFieldFilter.clear();
      listCustomers(null);
    });

    // Connect selected Customer to customerEditor or hide if none is selected
    grid.addSelectionListener(e -> {
      if (e.getSelected().isEmpty()) {
//customerEditor.setVisible(false);
      } else {
//customerEditor.editCustomer((Customer) grid.getSelectedRow());
      }
    });

    // Instantiate and edit new Customer the new button is clicked
//TODO: Add new record Button
buttonNewRecord.addClickListener(e -> showPopup(new Customer("", "", new Date(), "", countryDefault)));
buttonRefresh.addClickListener(e -> listCustomers(textFieldFilter.getValue()));


//buttonsPopup.addClickListener(e -> showPopup((Customer) grid.getSelectedRow()));

    // Listen changes made by the customerEditor, refresh data from backend
//customerEditor.setChangeHandler(() -> {
//  customerEditor.setVisible(false);
//  listCustomers(textFieldFilter.getValue());
//});

    // Initialize listing
    //TODO
    //listCustomers(null);


    // Repositories
//this.customerRepository = customerRepository;
    // Get First Country
//this.countryDefault = countryRepository.findOne(countryDefaultId);//findAll().get(0)

/*


    //Ui
    this.customerEditor = customerEditor;


    this.buttonsPopup =  new Button("Open popup", FontAwesome.PLUS);

    setSizeFull();

...

    //grid.addColumn("edit", FontIcon.class)
    //    .setRenderer(new FontIconRenderer(new ClickableRenderer.RendererClickListener() {
    //      @Override
    //      public void click(ClickableRenderer.RendererClickEvent e) {
    //        Notification.show("Editing item " + e.getItemId());
    //      }
    //    }));
    //
    //grid.addColumn("delete", FontIcon.class)
    //    .setRenderer(new FontIconRenderer(new ClickableRenderer.RendererClickListener() {
    //      @Override
    //      public void click(ClickableRenderer.RendererClickEvent e) {
    //        Notification.show("Deleted item " + e.getItemId());
    //      }
    //    }));
*/
  }

  /**
   * Generate GeneratedPropertyContainer for grid, with custom columns
   *
   * @param customerDataSource
   * @return
   */
  private GeneratedPropertyContainer getGeneratedPropertyContainer(List<Customer> customerDataSource) {

    // Get BeanItemContainer from DataSource
    BeanItemContainer beanItemContainer = new BeanItemContainer(Customer.class, customerDataSource);

    // Init GeneratedPropertyContainer, to store BeanItemContainer with custom Generated Properties
    GeneratedPropertyContainer generatedPropertyContainer = new GeneratedPropertyContainer(beanItemContainer);

    // Add Edit GeneratedProperty to BeanItemContainer
    generatedPropertyContainer.addGeneratedProperty("edit",
        new PropertyValueGenerator<String>() {
          @Override
          public String getValue(Item item, Object itemId, Object propertyId) {
            //Hack : Empty Caption, Using button
            return "<span style='visibility: hidden;'>Edit</span>";// "Edit" : The caption using text without .setRenderer(new HtmlRenderer());
            //return FontAwesome.PENCIL.getHtml();//require .setRenderer(new HtmlRenderer()); in column
          }

          @Override
          public Class<String> getType() {
            return String.class;
          }
        }
    );

    // Add Delete GeneratedProperty to BeanItemContainer
    generatedPropertyContainer.addGeneratedProperty("delete",
        new PropertyValueGenerator<String>() {
          @Override
          public String getValue(Item item, Object itemId, Object propertyId) {
            //Hack : Empty Caption, Using button
            return "<span style='visibility: hidden;'>Delete</span>"; //Del : The caption using text without .setRenderer(new HtmlRenderer());
            //return FontAwesome.PENCIL.getHtml();//require .setRenderer(new HtmlRenderer()); in column
          }

          @Override
          public Class<String> getType() {
            return String.class;
          }
        }
    );

    //generatedPropertyContainer.addGeneratedProperty("edit", new PropertyValueGenerator<Component>() {
    //  public Component getValue(Item item, Object itemId, Object propertyId) {
    //    HorizontalLayout component = new HorizontalLayout();
    //    Button earlyButton = new Button(null, new ThemeResource("images/clock.png"));
    //    Button publishButton = new Button(null, new ThemeResource("images/publish.png"));
    //    component.addComponents(new Button(FontAwesome.SAVE),earlyButton , publishButton);
    //    return component;
    //  }
    //  public Class<Component> getType() { return Component.class; }
    //});

    return generatedPropertyContainer;
  }

  // List customers/Update ContainerDataSource
  private void listCustomers(String text) {

    List<Customer> customerDataSource;

    if (StringUtils.isEmpty(text)) {
      customerDataSource = customerRepository.findAll();
    } else {
      customerDataSource = customerRepository.findByLastNameStartsWithIgnoreCase(text);
    }

    // Update GeneratedPropertyContainer with customers from customerDataSource
    GeneratedPropertyContainer generatedPropertyContainer = getGeneratedPropertyContainer(customerDataSource);
    // Update Grid ContainerDataSource to the custom GeneratedPropertyContainer
    grid.setContainerDataSource(generatedPropertyContainer);
  }

  private void showPopup(Customer customer) {

    // Assign customer to editForm Bean
    customerForm.editCustomer(customer);
    // Add it to the root component
    UI.getCurrent().addWindow(customerForm);
  }
}
