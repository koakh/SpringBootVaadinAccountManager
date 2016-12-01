package hello.ui;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.ui.*;
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
  private final CustomerEditor editor;
  private final Grid grid;
  private final TextField filter;
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
    this.editor = editor;
    this.grid = new Grid();
    this.filter = new TextField();
    this.buttonNewRecord = new Button("New customer", FontAwesome.PLUS);
    this.buttonsPopup =  new Button("Open popup", FontAwesome.PLUS);
  }

  @Override
  protected void init(VaadinRequest request) {
    // build layout
    HorizontalLayout actions = new HorizontalLayout(filter, buttonNewRecord, buttonsPopup);
    VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
    setContent(mainLayout);

    // configure layouts and components
    actions.setSpacing(true);
    mainLayout.setMargin(true);
    mainLayout.setSpacing(true);

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
    grid.removeColumn("id");
    grid.removeColumn("uuid");
    grid.removeColumn("email");
    grid.removeColumn("country");
    grid.removeColumn("status");
    // Must be here after setContainerDataSource
    // Columns that are not given for the method are placed after the specified columns in their natural order.
    grid.setColumnOrder("firstName", "lastName", "bornIn");
    //TODO
    //6.24.8. Filtering

    // TODO : Next Versions
    // Limit the visible properties, configure the Grid using the setColumns method to only show "firstName", "lastName" and "email" properties.
    //grid.setColumns("firstName", "lastName", "bornIn", "email");

    // assign placeHolder
    filter.setInputPrompt("Filter by Last Name");

    // Hook logic to components

    // Replace listing with filtered content when user changes filter
    filter.addTextChangeListener(e -> listCustomers(e.getText()));

    // Connect selected Customer to editor or hide if none is selected
    grid.addSelectionListener(e -> {
      if (e.getSelected().isEmpty()) {
        editor.setVisible(false);
      }
      else {
        editor.editCustomer((Customer) grid.getSelectedRow());
      }
    });

    // Instantiate and edit new Customer the new button is clicked
    buttonNewRecord.addClickListener(e -> editor.editCustomer(new Customer("", "", new Date(), "", countryDefault)));

    buttonsPopup.addClickListener(event -> showPopup());

    // Listen changes made by the editor, refresh data from backend
    editor.setChangeHandler(() -> {
      editor.setVisible(false);
      listCustomers(filter.getValue());
    });

    // Initialize listing
    listCustomers(null);
  }

  private void intitUI() {
    Accordion accordionMenu = new Accordion();
    accordionMenu.setWidth(100.0f, Unit.POINTS);
    accordionMenu.setHeight(100.0f, Unit.PERCENTAGE);

    for (int i = 1; i < 8; i++) {
      final VerticalLayout layout = new VerticalLayout(new Label("Text"));
      layout.setMargin(true);
      accordionMenu.addTab(layout, "Tab " + i);
    }
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