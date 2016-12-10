package com.koakh.accountmanager.ui.views.customer;

import com.vaadin.data.Item;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.server.FontIcon;

/**
 * Created by mario on 10/12/2016.
 */
public final class FontIconGenerator extends PropertyValueGenerator<String> {

  private final FontIcon fontIcon;

  public FontIconGenerator(FontIcon icon) {
    this.fontIcon = icon;
  }

  @Override
  public String getValue(Item item, Object itemId, Object propertyId) {
    return fontIcon.getHtml();
  }
  @Override
  public Class<String> getType() {
    return String.class;
  }
}
