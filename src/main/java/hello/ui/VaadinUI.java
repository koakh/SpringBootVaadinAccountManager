package hello.ui;

import com.vaadin.data.Item;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.themes.ValoTheme;
import hello.Application;
import hello.model.country.Country;
import hello.model.country.CountryRepository;
import hello.model.customer.Customer;
import hello.model.customer.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;

import java.util.Date;
import java.util.List;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  // Repositories
  private final CustomerRepository customerRepository;
  private final CustomerEditor customerEditor;
  // UI Components
  private Grid grid;
  private final TextField textFieldFilter;
  // Actions
  private final Button buttonClearFilter;
  private final Button buttonNewRecord;
  private final Button buttonsPopup;
  // Defaults
  private final Country countryDefault;
  //TODO
  //Inject Configuration Properties
  @Value("${model.faker.records.country.default}")
  private long countryDefaultId;

  @Autowired
  public VaadinUI(CustomerRepository customerRepository, CountryRepository countryRepository, CustomerEditor editor) {
    //Repositories
    this.customerRepository = customerRepository;
    // Get First Country
    this.countryDefault = countryRepository.findAll().get(0);
    //Ui
    this.customerEditor = editor;
    this.grid = null;
    this.textFieldFilter = new TextField();
    this.buttonClearFilter = new Button(FontAwesome.TIMES);
    this.buttonNewRecord = new Button("New customer", FontAwesome.PLUS);
    this.buttonsPopup =  new Button("Open popup", FontAwesome.PLUS);
    this.buttonClearFilter.setDescription("Clear the current filter");
  }

  @Override
  protected void init(VaadinRequest request) {

    // AccordionMenu
    Accordion accordionMenu = new Accordion();
    accordionMenu.setWidth(200.0f, Unit.POINTS);
    accordionMenu.setHeight(100.0f, Unit.PERCENTAGE);

    for (int i = 0; i < 6; i++) {
      final VerticalLayout layout = new VerticalLayout();
      Button button1 = new Button("Button1");
      button1.setWidth(100.0f, Unit.PERCENTAGE);
      Button button2 = new Button("Button1");
      button2.setWidth(100.0f, Unit.PERCENTAGE);
      layout.addComponent(button1);
      layout.addComponent(button2);
      layout.setSpacing(true);
      layout.setMargin(true);
      accordionMenu.addTab(layout, "Tab " + i, FontAwesome.ANDROID);
      //accordionMenu.getTab(i - 1).setIcon(FontAwesome.ANDROID);
    }

    // Box Filter and Clear Filter inside CssLayout, usefull in this case to group components in one component
    CssLayout cssLayoutFiltering = new CssLayout();
    cssLayoutFiltering.addComponents(textFieldFilter, buttonClearFilter);
    cssLayoutFiltering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

    // Build horizontalLayoutToolbar
    HorizontalLayout horizontalLayoutToolbar = new HorizontalLayout(cssLayoutFiltering, buttonNewRecord, buttonsPopup);
//horizontalLayoutToolbar.setHeight(100.0f, Unit.PERCENTAGE);

//HorizontalLayout main = new HorizontalLayout(grid, customerEditor);
//main.setSpacing(true);
//main.setSizeFull();
//grid.setSizeFull();
//main.setExpandRatio(grid, 1);



/*
    // Get customerDataSource from customerRepository and init customerDataSource
//List<Customer>
customerDataSource = customerRepository.findAll();
    // Create BeanItemContainer
//BeanItemContainer
beanItemContainer = new BeanItemContainer(Customer.class, customerDataSource);
    // Generate button caption column
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
*/

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
    grid.getColumn("bornIn").setHeaderCaption("Born In");
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

    // configure columns : grid.getColumns()
/*
    grid.getColumn("firstName").setHeaderCaption("First Name");
    grid.getColumn("lastName").setHeaderCaption("Last Name");
    grid.getColumn("bornIn").setHeaderCaption("Born In");
    // hide columns
//grid.removeColumn("id");
    grid.removeColumn("uuid");
    grid.removeColumn("email");
    grid.removeColumn("country");
    grid.removeColumn("status");
    grid.removeColumn("persisted");
    // Must be here after setContainerDataSource
    // Columns that are not given for the method are placed after the specified columns in their natural order.
    grid.setColumnOrder("firstName", "lastName", "bornIn");
    //TODO
    //6.24.8. Filtering
    //TODO
//grid.addColumn("id");
Grid.Column buttonColumn = grid.getColumn("id");
buttonColumn.setRenderer(new ButtonRenderer(event -> {
  showPopup();
  Notification.show("Editing item " + event.getItemId());
}));

*/


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


    // TODO : Next Versions
    // Limit the visible properties, configure the Grid using the setColumns method to only show "firstName", "lastName" and "email" properties.
    //grid.setColumns("firstName", "lastName", "bornIn", "email");

    // Build verticalLayoutMainContent
    VerticalLayout verticalLayoutMainContent = new VerticalLayout(horizontalLayoutToolbar, grid, customerEditor);
    verticalLayoutMainContent.setSizeFull();
    // Configure the grid to use all of the available space more efficiently.
    verticalLayoutMainContent.setExpandRatio(grid, 1);

    // Build Main Content
    HorizontalLayout horizontalLayoutMainContent = new HorizontalLayout(accordionMenu, verticalLayoutMainContent);
    horizontalLayoutMainContent.setSizeFull();
    // Configure the verticalLayoutMainContent to use all of the available space more efficiently.
    horizontalLayoutMainContent.setExpandRatio(verticalLayoutMainContent, 1);

    // Set UI Content
    //setContent(verticalLayoutMainContent);
    setContent(horizontalLayoutMainContent);

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

    buttonsPopup.addClickListener(event -> showPopup((Customer) grid.getSelectedRow()));

    // Listen changes made by the customerEditor, refresh data from backend
    customerEditor.setChangeHandler(() -> {
      customerEditor.setVisible(false);
      listCustomers(textFieldFilter.getValue());
    });

    // Initialize listing
    //TODO
//listCustomers(null);
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
  }
}