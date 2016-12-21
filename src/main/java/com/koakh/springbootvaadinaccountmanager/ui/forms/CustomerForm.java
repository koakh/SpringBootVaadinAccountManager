package com.koakh.springbootvaadinaccountmanager.ui.forms;

import com.koakh.springbootvaadinaccountmanager.model.country.Country;
import com.koakh.springbootvaadinaccountmanager.model.country.CountryRepository;
import com.koakh.springbootvaadinaccountmanager.model.customer.Customer;
import com.koakh.springbootvaadinaccountmanager.model.customer.CustomerRepository;
import com.koakh.springbootvaadinaccountmanager.model.customer.CustomerStatus;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by mario on 07/12/2016.
 */

@SpringComponent
@UIScope
public class CustomerForm extends BaseForm {

  // Autowired Beans
  private final CustomerRepository customerRepository;
  private final CountryRepository countryRepository;
  // Repository Object
  private Customer customer;
  // From Components : Field to edit Entity properties
  private TextField firstName = new TextField("First name");
  private TextField lastName = new TextField("Last name");
  private DateField bornIn = new DateField("Born In");
  private TextField email = new TextField("Email");
  private ComboBox country = new ComboBox("Select Country");
  private ComboBox status = new ComboBox("Select Customer Status");

  @Autowired
  public CustomerForm(CustomerRepository customerRepository, CountryRepository countryRepository) {

    super("Edit Customer", 400.0f, 380.0f); // Set window caption

    // Store Beans Reference in Attributes
    this.customerRepository = customerRepository;
    this.countryRepository = countryRepository;

    init();
  }

  private void init() {

    // Style Components
    firstName.setSizeFull();
    lastName.setSizeFull();
    bornIn.setSizeFull();
    email.setSizeFull();
    country.setSizeFull();
    status.setSizeFull();

    // Country DataSource
    //TODO: Show Items country.getItemIds()
    //https://vaadin.com/docs/-/part/framework/datamodel/datamodel-container.html
    country.setContainerDataSource(new BeanItemContainer(Country.class, countryRepository.findAll()));
    country.setItemCaptionMode(AbstractSelect.ItemCaptionMode.PROPERTY);
    country.setItemCaptionPropertyId("name");
    country.setNullSelectionAllowed(false);
    country.setFilteringMode(FilteringMode.CONTAINS);

    //Status DataSource
    status.addItems(CustomerStatus.values());
    status.setNullSelectionAllowed(false);

    // Add components to VerticalLayout
    VerticalLayout verticalLayout = new VerticalLayout();
    verticalLayout.addComponents(firstName, lastName, bornIn, email, country, status);
    verticalLayout.setMargin(true);

    // Place in Window Content
    contentPanel.setContent(verticalLayout);
  }

  public void editCustomer(Customer customer) {

    final boolean persisted = customer.getId() != null;

    if (persisted) {
      // Find fresh entity for editing
      this.customer = customerRepository.findOne(customer.getId());
    }
    else {
      this.customer = customer;
    }

    // Bind customer properties to similarly named fields
    // Could also use annotation or "manual binding" or programmatically
    // moving values from fields to entities before saving
    //
    // Bean validators are automatically created when using a BeanFieldGroup.
    //
    // Returns the bean field group used to make binding
    // ((BeanItem) ((BeanFieldGroup) beanFieldGroup).itemDataSource).getItemPropertyIds()
    Object beanFieldGroup = BeanFieldGroup.bindFieldsUnbuffered(this.customer, this);

    //TODO
    // Add extra Validators
    //firstName.addValidator(new RegexpValidator("/^(?:[\\u00c0-\\u01ffa-zA-Z'-]){2,}(?:\\s[\\u00c0-\\u01ffa-zA-Z'-]{2,})+$/i", true, "Error Message"));

    // Select all text in firstName field automatically
    firstName.selectAll();
  }

  @Override
  protected void saveAction() {
    super.saveAction();

    customerRepository.save(customer);
  }

  @Override
  protected void deleteAction() {
    super.deleteAction();

    customerRepository.delete(customer);
  }
}
