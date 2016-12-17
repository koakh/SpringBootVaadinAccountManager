package com.koakh.springbootvaadinaccountmanager.ui.renderers;

import com.vaadin.ui.renderers.HtmlRenderer;
import elemental.json.JsonValue;

/**
 * Created by mario on 10/12/2016.
 */
public class BoldLastNameRenderer extends HtmlRenderer {

  @Override
  public JsonValue encode(String value) {
    int lastSpace = value.lastIndexOf(' ');
    if (lastSpace >= 0) {
      value = String
          .format("<span style=\"pointer-events: none;\">%s<b>%s</b></span>",
              value.substring(0, lastSpace),
              value.substring(lastSpace));
    }
    return super.encode(value);
  }
}