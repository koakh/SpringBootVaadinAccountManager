package hello.ui;

import hello.model.country.Country;
import hello.model.country.CountryRepository;
import hello.model.customer.Customer;
import hello.model.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.util.Date;
import java.util.List;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

  private final CustomerRepository repository;
  private final CustomerEditor editor;
  private final Grid grid;
  private final TextField filter;
  private final Button buttonsNewRecord;
  private final Country countryDefault;
//Inject Configuration Properties
//@Value("${model.faker.records.country.default}")
//private long countryDefaultId = 10L;


  @Autowired
  public VaadinUI(CustomerRepository repository, CountryRepository countryRepository, CustomerEditor editor) {
    this.repository = repository;
    this.editor = editor;
    this.grid = new Grid();
    this.filter = new TextField();
    this.buttonsNewRecord = new Button("New customer", FontAwesome.PLUS);
    this.countryDefault = countryRepository.findAll().get(0);
  }

  @Override
  protected void init(VaadinRequest request) {
    // build layout
    HorizontalLayout actions = new HorizontalLayout(filter, buttonsNewRecord);
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
    grid.getColumn("email").setHeaderCaption("Email");
    // hide columns
    grid.removeColumn("id");
    grid.removeColumn("uuid");
    grid.removeColumn("email");
    grid.removeColumn("country");
    grid.removeColumn("status");
    // Must be here after setContainerDataSource
    // Columns that are not given for the method are placed after the specified columns in their natural order.
    grid.setColumnOrder("firstName", "lastName", "bornIn");

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
    buttonsNewRecord.addClickListener(e -> editor.editCustomer(new Customer("", "", new Date(), "", countryDefault)));

    // Listen changes made by the editor, refresh data from backend
    editor.setChangeHandler(() -> {
      editor.setVisible(false);
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
}