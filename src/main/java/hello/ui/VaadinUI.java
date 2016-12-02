package hello.ui;

import com.vaadin.event.ShortcutAction;
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

  private final CustomerRepository repository;
  private final CustomerEditor customerEditor;
  private final Grid grid;
  private final TextField filter;
  private final Button clearFilterTextBtn;
  private final Button buttonNewRecord;
  private final Button buttonsPopup;
  private final Country countryDefault;
  //TODO
  //Inject Configuration Properties
  @Value("${model.faker.records.country.default}")
  private long countryDefaultId = 10L;

  @Autowired
  public VaadinUI(CustomerRepository repository, CountryRepository countryRepository, CustomerEditor editor) {
    this.repository = repository;
    this.countryDefault = countryRepository.findAll().get(0);
    //Ui
    this.customerEditor = editor;
    this.grid = new Grid();
    this.filter = new TextField();
    this.clearFilterTextBtn = new Button(FontAwesome.TIMES);
    this.buttonNewRecord = new Button("New customer", FontAwesome.PLUS);
    this.buttonsPopup =  new Button("Open popup", FontAwesome.PLUS);
    // Changes some props of initialized componemts
    grid.setSizeFull();
    this.clearFilterTextBtn.setDescription("Clear the current filter");
  }

  @Override
  protected void init(VaadinRequest request) {

    // AccordionMenu
    Accordion accordionMenu = new Accordion();
    accordionMenu.setWidth(200.0f, Unit.POINTS);
    accordionMenu.setHeight(100.0f, Unit.PERCENTAGE);

//accordionMenu.getSelectedTab()
//accordionMenu.setSelectedTab();
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
    cssLayoutFiltering.addComponents(filter, clearFilterTextBtn);
    cssLayoutFiltering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

    // Build horizontalLayoutToolbar
    HorizontalLayout horizontalLayoutToolbar = new HorizontalLayout(cssLayoutFiltering, buttonNewRecord, buttonsPopup);
//horizontalLayoutToolbar.setHeight(100.0f, Unit.PERCENTAGE);

//HorizontalLayout main = new HorizontalLayout(grid, customerEditor);
//main.setSpacing(true);
//main.setSizeFull();
//grid.setSizeFull();
//main.setExpandRatio(grid, 1);

    // Build verticalLayoutMainContent
    VerticalLayout verticalLayoutMainContent = new VerticalLayout(horizontalLayoutToolbar, grid, customerEditor);
    verticalLayoutMainContent.setSizeFull();
    // Configure the grid to use all of the available space more efficiently.
    verticalLayoutMainContent.setExpandRatio(grid, 1);

    // Build Main Content
    HorizontalLayout horizontalLayoutMainContent = new HorizontalLayout(accordionMenu, verticalLayoutMainContent);
//horizontalLayoutMainContent.setMargin(true);
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

    // repository
    List<Customer> customerDataSource = repository.findAll();

    //grid.setHeight(300, Unit.PIXELS);
    grid.setSizeFull();
    grid.setContainerDataSource(new BeanItemContainer(Customer.class, customerDataSource));
    // configure columns : grid.getColumns()
    grid.getColumn("firstName").setHeaderCaption("First Name");
    grid.getColumn("lastName").setHeaderCaption("Last Name");
    grid.getColumn("bornIn").setHeaderCaption("Born In");
    // hide columns
//grid.removeColumn("id");
    grid.removeColumn("uuid");
    grid.removeColumn("email");
    grid.removeColumn("country");
    grid.removeColumn("status");
    // Must be here after setContainerDataSource
    // Columns that are not given for the method are placed after the specified columns in their natural order.
    grid.setColumnOrder("firstName", "lastName", "bornIn");
    //TODO
    //6.24.8. Filtering
    //TODO
//grid.addColumn("id");
Grid.Column buttonColumn = grid.getColumn("id");
buttonColumn.setRenderer(new ButtonRenderer(event -> showPopup()));

    // TODO : Next Versions
    // Limit the visible properties, configure the Grid using the setColumns method to only show "firstName", "lastName" and "email" properties.
    //grid.setColumns("firstName", "lastName", "bornIn", "email");

    // assign placeHolder
    filter.setInputPrompt("Filter by Last Name");

    // Hook logic to components

    // Replace listing with filtered content when user changes filter
    filter.addTextChangeListener(e -> listCustomers(e.getText()));

    clearFilterTextBtn.addClickListener(e -> {
      filter.clear();
      listCustomers(null);
    });

    // Connect selected Customer to customerEditor or hide if none is selected
    grid.addSelectionListener(e -> {
      if (e.getSelected().isEmpty()) {
        customerEditor.setVisible(false);
      }
      else {
        customerEditor.editCustomer((Customer) grid.getSelectedRow());
      }
    });

    // Instantiate and edit new Customer the new button is clicked
    buttonNewRecord.addClickListener(e -> customerEditor.editCustomer(new Customer("", "", new Date(), "", countryDefault)));

    buttonsPopup.addClickListener(event -> showPopup());

    // Listen changes made by the customerEditor, refresh data from backend
    customerEditor.setChangeHandler(() -> {
      customerEditor.setVisible(false);
      listCustomers(filter.getValue());
    });

    // Initialize listing
    listCustomers(null);
  }

  // List customers/Update ContainerDataSource
  private void listCustomers(String text) {
    if (StringUtils.isEmpty(text)) {
      grid.setContainerDataSource(new BeanItemContainer(
          Customer.class, repository.findAll()
      ));
    }
    else {
      grid.setContainerDataSource(new BeanItemContainer(
          Customer.class, repository.findByLastNameStartsWithIgnoreCase(text)
      ));
    }
  }

  private void showPopup() {

    // Create a sub-window and set the content
    Window window = new Window("Sub-window");
    window.setDraggable(true);
    window.setResizable(false);
    window.setCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
    //window.setWidth(300.0f, Unit.PIXELS);
    //window.setHeight(300.0f, Unit.PIXELS);

    //Vertical Content
    VerticalLayout subContent = new VerticalLayout();
    subContent.setMargin(true);
    window.setContent(subContent);

    // Put some components in it
    subContent.addComponent(new Label("Meatball sub"));
    subContent.addComponent(new Button("Awlright"));

    // Center it in the browser window
    window.center();

    // Modal
    window.setModal(true);

    // Open it in the UI
    addWindow(window);
  }
}