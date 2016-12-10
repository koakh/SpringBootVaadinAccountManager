package com.koakh.accountmanager.ui.views.customer;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.koakh.accountmanager.Application;
import com.koakh.accountmanager.ui.forms.CustomerForm;
import com.koakh.accountmanager.model.country.Country;
import com.koakh.accountmanager.model.customer.Customer;
import com.koakh.accountmanager.model.customer.CustomerRepository;
import com.koakh.accountmanager.ui.forms.CustomerEditor;
import com.koakh.accountmanager.ui.views.IViewNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  // Repositories
  private CustomerRepository customerRepository;
  private CustomerEditor customerEditor;
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

  //@Autowired
  //private CustomerRepository customerRepository;
  //@Autowired
  //private CountryRepository countryRepository;
  //@Autowired
  //private CustomerEditor customerEditor;

  //@Autowired
  public CustomerView(/*CustomerRepository customerRepository, CountryRepository countryRepository, CustomerEditor customerEditor*/) {
  }

//@Autowired
  @PostConstruct
  void init(/*CustomerRepository customerRepository, CountryRepository countryRepository, CustomerEditor customerEditor*/) {
    // Repositories
//this.customerRepository = customerRepository;
    // Get First Country
//this.countryDefault = countryRepository.findOne(countryDefaultId);//findAll().get(0)

    log.info("Im here");


/*


    //Ui
    this.customerEditor = customerEditor;
    this.textFieldFilter = new TextField();
    this.buttonClearFilter = new Button(FontAwesome.TIMES);
    this.buttonNewRecord = new Button("New customer", FontAwesome.PLUS);
    this.buttonsPopup =  new Button("Open popup", FontAwesome.PLUS);
    this.buttonClearFilter.setDescription("Clear the current filter");

    setSizeFull();

    // Box Filter and Clear Filter inside CssLayout, usefull in this case to group components in one component
    CssLayout cssLayoutFiltering = new CssLayout();
    cssLayoutFiltering.addComponents(textFieldFilter, buttonClearFilter);
    cssLayoutFiltering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

    // Build horizontalLayoutToolbar
    HorizontalLayout horizontalLayoutToolbar = new HorizontalLayout(cssLayoutFiltering, buttonNewRecord, buttonsPopup);

    // Start with a GeneratedPropertyContainer with all customers from repository
    GeneratedPropertyContainer generatedPropertyContainer = getGeneratedPropertyContainer(customerRepository.findAll());

    // Create a grid, and assign GeneratedPropertyContainer (SetContainerDataSource to generatedPropertyContainer with buttons)
    grid = new Grid(generatedPropertyContainer);
    grid.setSizeFull();
    // Reconfigure columns, Model + New Properties
    grid.removeAllColumns();
    grid.addColumn("firstName");
    grid.addColumn("lastName");
    grid.addColumn("email");
    grid.addColumn("edit");
    grid.addColumn("delete");
    // Configure columns : grid.getColumns()
    grid.getColumn("firstName").setHeaderCaption("First Name");
    grid.getColumn("lastName").setHeaderCaption("Last Name");
    // Join Action Columns
    grid.getDefaultHeaderRow().join("edit", "delete").setText("Actions");

    // Render a button that edits the data row (item)
    grid.getColumn("edit")
        .setRenderer(new ButtonRenderer(
            e -> showPopup((Customer) e.getItemId()))
        ).setWidth(100);

    // Render a button that deletes the data row (item)
    grid.getColumn("delete")
        .setRenderer(new ButtonRenderer(e ->
        {
          Customer customer = (Customer) e.getItemId();
          // Remove from grid
          grid.getContainerDataSource().removeItem(customer);
          //Remove from Repository
          //TODO : Required using getId()
          customerRepository.delete(customer.getId());
        }
        )).setWidth(100);

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

    // Build verticalLayoutMainContent
    VerticalLayout verticalLayoutMainContent = new VerticalLayout(horizontalLayoutToolbar, grid, customerEditor);
    verticalLayoutMainContent.setSizeFull();
    // Configure the grid to use all of the available space more efficiently.
    verticalLayoutMainContent.setExpandRatio(grid, 1);

//// Build Main Content
//HorizontalLayout horizontalLayoutMainContent = new HorizontalLayout(accordionMenu, verticalLayoutMainContent);
//horizontalLayoutMainContent.setSizeFull();
//// Configure the verticalLayoutMainContent to use all of the available space more efficiently.
//horizontalLayoutMainContent.setExpandRatio(verticalLayoutMainContent, 1);


    // Set UI Content
    addComponent(verticalLayoutMainContent);

    // configure layouts and components
    horizontalLayoutToolbar.setSpacing(true);
    verticalLayoutMainContent.setMargin(true);
    verticalLayoutMainContent.setSpacing(true);

    // assign placeHolder
    textFieldFilter.setInputPrompt("Filter by Last Name");

    // Hook logic to components

    // Replace listing with filtered content when user changes filter
    textFieldFilter.addTextChangeListener(e -> listCustomers(e.getText()));

    buttonClearFilter.addClickListener(e -> {
      textFieldFilter.clear();
      listCustomers(null);
    });

    // Connect selected Customer to customerEditor or hide if none is selected
    grid.addSelectionListener(e -> {

      //TODO : Remove this Comment
      //Fix for You are using toString() instead of getValue()
      //Customer selected = (Customer) e.getSelected().iterator().next();

      if (e.getSelected().isEmpty()) {
        customerEditor.setVisible(false);
      }
      else {
        customerEditor.editCustomer((Customer) grid.getSelectedRow());
      }
    });

    // Instantiate and edit new Customer the new button is clicked
    buttonNewRecord.addClickListener(e -> customerEditor.editCustomer(new Customer("", "", new Date(), "", countryDefault)));

    buttonsPopup.addClickListener(e -> showPopup((Customer) grid.getSelectedRow()));

    // Listen changes made by the customerEditor, refresh data from backend
    customerEditor.setChangeHandler(() -> {
      customerEditor.setVisible(false);
      listCustomers(textFieldFilter.getValue());
    });

    // Initialize listing
    //TODO
    //listCustomers(null);

*/
  }

  @Override
  public void enter(ViewChangeListener.ViewChangeEvent event) {

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
            return "Edit"; // The caption
          }
          @Override
          public Class<String> getType() {
            return String.class;
          }
        }
    );

    //http://stackoverflow.com/questions/29922292/fill-buttons-in-grid-with-fontawesome-icons-and-add-tooltips-to-them
    //https://github.com/FokkeZB/IconFont/blob/master/test/font-awesome.tss
    //generatedPropertyContainer.addGeneratedProperty("edit", new PropertyValueGenerator<String>() {
    //  @Override
    //  public String getValue(Item item, Object itemId, Object propertyId) {
    //    return FontAwesome.WARNING.getHtml();
    //  }
    //
    //  @Override
    //  public Class<String> getType() {
    //    return String.class;
    //  }
    //});

    // Add Delete GeneratedProperty to BeanItemContainer
    generatedPropertyContainer.addGeneratedProperty("delete",
        new PropertyValueGenerator<String>() {
          @Override
          public String getValue(Item item, Object itemId, Object propertyId) {
            return "Delete"; // The caption
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

  private void showPopup(Customer item) {
/*
    log.info(item.toString());

    // Create a sub-window and set the content
    Window window = new Window("Sub-window");
    window.setDraggable(true);
    window.setResizable(false);
    window.setWidth(300.0f, Unit.PIXELS);
    window.setHeight(300.0f, Unit.PIXELS);

    //Vertical Content
    VerticalLayout subContent = new VerticalLayout();
    subContent.setMargin(true);
    window.setContent(subContent);

    // Put some components in it
    subContent.addComponent(new Label(item.getFirstName()));
    subContent.addComponent(new Button("Awlright"));

    // Center it in the browser window
    window.center();

    // Modal
    window.setModal(true);

    // Open it in the UI
    addWindow(window);
*/

    CustomerForm customerForm = new CustomerForm();

    // Add it to the root component
    UI.getCurrent().addWindow(customerForm);
  }
}
