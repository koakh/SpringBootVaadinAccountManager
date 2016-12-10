package com.koakh.accountmanager.ui.views.customer;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.koakh.accountmanager.Application;
import com.koakh.accountmanager.ui.forms.CustomerForm;
import com.koakh.accountmanager.model.country.Country;
import com.koakh.accountmanager.model.customer.Customer;
import com.koakh.accountmanager.model.customer.CustomerRepository;
import com.koakh.accountmanager.ui.forms.CustomerEditor;
import com.vaadin.ui.renderers.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
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
  private Button buttonsPopup;
  // Defaults
  private Country countryDefault;

  @Value("${model.faker.records.country.default}")
  private long countryDefaultId;

  @Autowired
  private CustomerRepository customerRepository;

  @PostConstruct
  void init() {
    setSizeFull();
    //addStyleName(ValoTheme.PANEL_BORDERLESS);

    // Compose Toolbar UI Objects
    textFieldFilter = new TextField();
    buttonClearFilter = new Button("Clear filter", FontAwesome.TIMES);
    buttonNewRecord = new Button("New customer", FontAwesome.PLUS);
    // Assign placeHolder, used here for using method setInputPrompt only
    textFieldFilter.setInputPrompt("Filter by Last Name");

    // Box Filter and Clear Filter inside CssLayout, usefull in this case to group components in one component
    CssLayout cssLayoutFiltering = new CssLayout();
    cssLayoutFiltering.addComponents(textFieldFilter, buttonClearFilter);
    cssLayoutFiltering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

    // Build horizontalLayoutToolbar to pack Toolbar UI Objects
    HorizontalLayout horizontalLayoutToolbar = new HorizontalLayout(cssLayoutFiltering, buttonNewRecord);
    horizontalLayoutToolbar.setSpacing(true);

    // Start with a GeneratedPropertyContainer with all customers from repository, with custom properties(columns) added to model for action buttons
    GeneratedPropertyContainer generatedPropertyContainer = getGeneratedPropertyContainer(customerRepository.findAll());

    // Create a Grid, and assign GeneratedPropertyContainer same has SetContainerDataSource
    grid = new Grid(generatedPropertyContainer);
    grid.setSizeFull();
    // Reconfigure columns, Removing all from default model and add desires ones with actions buttons
    grid.removeAllColumns();
    // Model Columns
    grid.addColumn("id").setHeaderCaption("##").setRenderer(new NumberRenderer("%02d")).setExpandRatio(0);
    grid.addColumn("firstName").setHeaderCaption("First Name").setExpandRatio(2);
    grid.addColumn("lastName").setHeaderCaption("Last Name").setExpandRatio(2);
    grid.addColumn("email").setHeaderCaption("Last Name").setExpandRatio(4);
    // Action Buttons
    grid.addColumn("edit").setHeaderCaption("Edit");//.setRenderer(new HtmlRenderer());
    grid.addColumn("delete").setHeaderCaption("Delete");//.setRenderer(new HtmlRenderer());
    // Configure columns : show all columns grid.getColumns(), and get grid.getColumn("columnName")
    // Join Action Columns
    grid.getDefaultHeaderRow().join("edit", "delete").setText("Actions");

    // Render a button that edits the data row (item)
    grid.getColumn("edit")
    .setRenderer(new ButtonRenderer(
        e -> showPopup((Customer) e.getItemId()))
    ).setWidth(100).setResizable(false);

    // Render a button that deletes the data row (item)
    grid.getColumn("delete")
        .setRenderer(new ButtonRenderer(e ->
        {
          Customer customer = (Customer) e.getItemId();
          // Remove from grid
          grid.getContainerDataSource().removeItem(customer);
          //TODO : Required using getId()
          //Remove from Repository
          customerRepository.delete(customer.getId());
        }
        )).setWidth(100).setResizable(false);

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
      }
      else {
//customerEditor.editCustomer((Customer) grid.getSelectedRow());
      }
    });

    // Instantiate and edit new Customer the new button is clicked
//buttonNewRecord.addClickListener(e -> customerEditor.editCustomer(new Customer("", "", new Date(), "", countryDefault)));

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

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {
    // This view is constructed in the init() method()

    Notification.show("Welcome to CustomerMain View");
  }

  /**
   * Generate GeneratedPropertyContainer for grid, with custom columns
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
            return "Edit"; // The caption using text without .setRenderer(new HtmlRenderer());
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
            return "Del"; // The caption using text without .setRenderer(new HtmlRenderer());
            //return FontAwesome.PENCIL.getHtml();//require .setRenderer(new HtmlRenderer()); in column
          }
          @Override
          public Class<String> getType() {
            return String.class;
          }
        }
    );

    return generatedPropertyContainer;
  }

  // List customers/Update ContainerDataSource
  private void listCustomers(String text) {

    List<Customer> customerDataSource;

    if (StringUtils.isEmpty(text)) {
      customerDataSource = customerRepository.findAll();
    }
    else {
      customerDataSource = customerRepository.findByLastNameStartsWithIgnoreCase(text);
    }

    // Update GeneratedPropertyContainer with customers from customerDataSource
    GeneratedPropertyContainer generatedPropertyContainer = getGeneratedPropertyContainer(customerDataSource);
    // Update Grid ContainerDataSource to the custom GeneratedPropertyContainer
    grid.setContainerDataSource(generatedPropertyContainer);
  }

  private void showPopup(Customer customer) {

    // Init Form
    CustomerForm customerForm = new CustomerForm(customer);
    // Add it to the root component
    UI.getCurrent().addWindow(customerForm);
  }
}
