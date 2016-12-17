package com.koakh.springbootvaadinaccountmanager.ui.renderers;

/**
 * Created by mario on 10/12/2016.
 */

import com.vaadin.ui.renderers.ClickableRenderer;

/**
 * TODO write JAVADOC!!!
 * https://github.com/Koziolek/FontAwesomeRenderer/blob/master/FontAwesomeRenderer/src/main/java/pl/koziolekweb/FontIconRenderer.java
 */
public class FontIconRenderer extends ClickableRenderer<String> {

  /**
   * Creates a new icon renderer.
   */
  public FontIconRenderer() {
    super(String.class);
  }

  /**
   * Creates a new icon renderer and adds the given click listener to it.
   *
   * @param listener the click listener to register
   */
  public FontIconRenderer(RendererClickListener listener) {
    this();
    addClickListener(listener);
  }
}
