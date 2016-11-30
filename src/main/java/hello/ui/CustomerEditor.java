package hello.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;
import hello.model.country.Country;
import hello.model.country.CountryRepository;
import hello.model.customer.Customer;
import hello.model.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

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

  /* Fields to edit properties in Customer entity */
  TextField firstName = new TextField("First name");
  TextField lastName = new TextField("Last name");
  DateField bornIn = new DateField("Born In");
  TextField email = new TextField("Email");
  ComboBox country = new ComboBox("Select your country");

  /* Action buttons */
  Button save = new Button("Save", FontAwesome.SAVE);
  Button cancel = new Button("Cancel");
  Button delete = new Button("Delete", FontAwesome.TRASH_O);
  CssLayout actions = new CssLayout(save, cancel, delete);

  @Autowired
  public CustomerEditor(CustomerRepository customerRepository, CountryRepository countryRepository) {
    this.customerRepository = customerRepository;
    this.countryRepository = countryRepository;

    //TODO:
    //country.setContainerDataSource(new BeanItemContainer(Customer.class, countryRepository.findAll()));

    addComponents(firstName, lastName, bornIn, email, actions);

    // Configure and style components
    setSpacing(true);
    actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
    save.setStyleName(ValoTheme.BUTTON_PRIMARY);
    save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

    // wire action buttons to save, delete and reset
    save.addClickListener(e -> customerRepository.save(customer));
    delete.addClickListener(e -> customerRepository.delete(customer));
    cancel.addClickListener(e -> editCustomer(customer));
    setVisible(false);
  }

  public interface ChangeHandler {

    void onChange();
  }

  public final void editCustomer(Customer c) {
    final boolean persisted = c.getId() != null;
    if (persisted) {
      // Find fresh entity for editing
      customer = customerRepository.findOne(c.getId());
    }
    else {
      customer = c;
    }
    cancel.setVisible(persisted);

    // Bind customer properties to similarly named fields
    // Could also use annotation or "manual binding" or programmatically
    // moving values from fields to entities before saving
    BeanFieldGroup.bindFieldsUnbuffered(customer, this);

    setVisible(true);

    // A hack to ensure the whole form is visible
    save.focus();
    // Select all text in firstName field automatically
    firstName.selectAll();
  }

  public void setChangeHandler(ChangeHandler h) {
    // ChangeHandler is notified when either save or delete
    // is clicked
    save.addClickListener(e -> h.onChange());
    delete.addClickListener(e -> h.onChange());
  }
}