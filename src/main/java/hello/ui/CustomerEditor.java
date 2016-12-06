package hello.ui;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import hello.model.country.Country;
import hello.model.country.CountryRepository;
import hello.model.customer.Customer;
import hello.model.customer.CustomerRepository;
import hello.model.customer.CustomerStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A simple example to introduce building forms. As your real application is
 * probably much more complicated than this example, you could re-use this form in
 * multiple places. This example component is only used in VaadinUI.
 * <p>
 * In a real world application you'll most likely using a common super class for all your
 * forms - less code, better UX. See e.g. AbstractForm in Virin
 * (https://vaadin.com/addon/viritin).
 */
@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout {

  private final CustomerRepository customerRepository;
  private final CountryRepository countryRepository;

  /**
   * The currently edited customer
   */
  private Customer customer;
  // Fields to edit properties in Customer entity
  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private DateField bornIn = new DateField("Born In");
  private TextField email = new TextField("Email");
  private ComboBox country = new ComboBox("Select Country");
  private ComboBox status = new ComboBox("Select Customer Status");
  // Action buttons
  private Button save = new Button("Save", FontAwesome.SAVE);
  private Button cancel = new Button("Cancel", FontAwesome.TIMES);
  private Button delete = new Button("Delete", FontAwesome.TRASH_O);
  // Layout
  private CssLayout actions = new CssLayout(save, cancel, delete);

  @Autowired
  public CustomerEditor(CustomerRepository customerRepository, CountryRepository countryRepository) {

    this.customerRepository = customerRepository;
    this.countryRepository = countryRepository;

    // Configure Buttons
    save.setStyleName(ValoTheme.BUTTON_PRIMARY);
    delete.setStyleName(ValoTheme.BUTTON_DANGER);
    // ShortcutActions
    save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    delete.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);

    //TODO: Show Items country.getItemIds()
    //https://vaadin.com/docs/-/part/framework/datamodel/datamodel-container.html
    country.setContainerDataSource(new BeanItemContainer(Country.class, countryRepository.findAll()));
    //Country countryRecord = countryRepository.findAll().get(50);
    //country.setValue(countryRecord.getId());
    //country.setItemCaption(countryRecord.getId(), countryRecord.getName());
    // Use the name property for item captions
    //country.setItemCaptionPropertyId("name");
    // show caption property
    country.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
    country.setItemCaptionPropertyId("name");
    country.setNullSelectionAllowed(false);
    country.setFilteringMode(FilteringMode.CONTAINS);
    //country.setValue(country.getItemIds().iterator().next());
    //country.select(countryRecord.getId());
    //country.getItemIds().iterator().next()
    //https://vaadin.com/api/com/vaadin/ui/AbstractSelect.html#setValue-java.lang.Object-

    // Create the selection component
    //comboBox.addContainerProperty("name", Country.class, "'Bolivia'");
    // Add some items (here by the item ID as the caption)
    //Object item = comboBox.addItem();
    //item.getItemProperty("name").setValue("Sun");
    // Access a property in the item
    //comboBox.addItems("Mercury", "Venus", "Earth", "Jupiter");
    //comboBox.setNullSelectionAllowed(false);
    //comboBox.setValue("Earth");

    //Customer Status
    status.addItems(CustomerStatus.values());
    status.setNullSelectionAllowed(false);

    // Add components to VerticalLayout
    addComponents(firstName, lastName, bornIn, email, country, status, actions);

    // Configure and style components
    setSpacing(true);
    actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    save.setStyleName(ValoTheme.BUTTON_PRIMARY);
    save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

    // wire action buttons to save, delete and reset
    save.addClickListener(e -> customerRepository.save(customer));
    delete.addClickListener(e ->
        //TODO : Required using getId()
        customerRepository.delete(customer.getId())
    );
    cancel.addClickListener(e -> editCustomer(customer));

    //Show Form
    setVisible(false);
  }

  public interface ChangeHandler {
    void onChange();
  }

  public final void editCustomer(Customer customer) {
    final boolean persisted = customer.getId() != null;

    if (persisted) {
      // Find fresh entity for editing
      this.customer = customerRepository.findOne(customer.getId());
    }
    else {
      this.customer = customer;
    }
    // Show DDelete|Cancel button for only customers already in the database
    delete.setVisible(customer.isPersisted());
    cancel.setVisible(persisted);

    // Bind customer properties to similarly named fields
    // Could also use annotation or "manual binding" or programmatically
    // moving values from fields to entities before saving
    //
    // Bean validators are automatically created when using a BeanFieldGroup.
    //
    // Returns the bean field group used to make binding
    //
    // ((BeanItem) ((BeanFieldGroup) beanFieldGroup).itemDataSource).getItemPropertyIds()
    Object beanFieldGroup = BeanFieldGroup.bindFieldsUnbuffered(this.customer, this);

    //TODO
    // Add extra Validators
    //firstName.addValidator(new RegexpValidator("/^(?:[\\u00c0-\\u01ffa-zA-Z'-]){2,}(?:\\s[\\u00c0-\\u01ffa-zA-Z'-]{2,})+$/i", true, "Error Message"));

    // Show this Form
    setVisible(true);

    // A hack to ensure the whole form is visible
    save.focus();

    // Select all text in firstName field automatically
    firstName.selectAll();
  }

  public void setChangeHandler(ChangeHandler h) {
    // ChangeHandler is notified when either save or delete is clicked
    save.addClickListener(e -> h.onChange());
    delete.addClickListener(e -> h.onChange());
  }
}