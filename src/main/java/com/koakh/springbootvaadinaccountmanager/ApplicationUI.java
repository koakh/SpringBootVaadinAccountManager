package com.koakh.springbootvaadinaccountmanager;

import com.koakh.springbootvaadinaccountmanager.ui.views.customer.CustomerView;
import com.koakh.springbootvaadinaccountmanager.ui.views.system.AccessDeniedView;
import com.koakh.springbootvaadinaccountmanager.ui.views.system.ErrorView;
import com.koakh.springbootvaadinaccountmanager.ui.views.system.HelpView;
import com.koakh.springbootvaadinaccountmanager.ui.views.test.*;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by mario on 09/12/2016.
 * @SpringUI: annotation is the way in which you let the Vaadin Spring plugin know which UI’s should be accessible to the user and how they should be mapped
 * @Autowired: instance of the greeter has been injected to the UI.
 *
 * To define where in the UI the views are to be shown, a bean or a class implementing ViewDisplay or extending (Single)ComponentContainer should be annotated with
 * @SpringViewDisplay. In the simplest cases, this can be the UI class itself.
 * Also this view is automatically registered as it has the @SpringView annotation,
 * so after annotating the UI with @SpringViewDisplay we would be able to open it by opening http://localhost:8080#!view
 */


@SpringUI
@SpringViewDisplay
@Theme("SpringBootVaadinAccountManager")
public class ApplicationUI extends UI implements ViewDisplay {

	private static final Logger log = LoggerFactory.getLogger(ApplicationUI.class);

	// Application Properties
	@Value("${app.name}")
	private String appName;
	// Main View Display Panel, to house Views
	private Panel springViewDisplay;

	@Autowired
	SpringViewProvider springViewProvider;

	@Override
	public void showView(View view) {

		springViewDisplay.setContent((Component) view);
	}

	@Override
	protected void init(VaadinRequest request){

		//Set PageName
		getPage().setTitle(appName);

		// AccordionMenu
		final Accordion accordionMenu = new Accordion();
		accordionMenu.setWidth(200.0f, Unit.POINTS);
		accordionMenu.setHeight(100.0f, Unit.PERCENTAGE);

		// AccordionMenu: Page1
		final VerticalLayout verticalLayoutPage1 = new VerticalLayout();
		verticalLayoutPage1.addComponent(createNavigationButton("Default View", DefaultView.VIEW_NAME, FontAwesome.APPLE, true));
		verticalLayoutPage1.addComponent(createNavigationButton("View Scoped View", ViewScopedView.VIEW_NAME, FontAwesome.BINOCULARS, true));
		verticalLayoutPage1.addComponent(createNavigationButton("Help View", HelpView.VIEW_NAME, FontAwesome.BATTERY_0, true));
		verticalLayoutPage1.addComponent(createNavigationButton("Customer View", CustomerView.VIEW_NAME, FontAwesome.GITHUB, true));
		verticalLayoutPage1.setSpacing(true);
		verticalLayoutPage1.setMargin(true);
		accordionMenu.addTab(verticalLayoutPage1, "Page1", FontAwesome.AMAZON);
		// AccordionMenu: Page2
		final VerticalLayout verticalLayoutPage2 = new VerticalLayout();
		verticalLayoutPage2.addComponent(createNavigationButton("Default View", DefaultView.VIEW_NAME, FontAwesome.APPLE, true));
		verticalLayoutPage2.addComponent(createNavigationButton("View Scoped View", ViewScopedView.VIEW_NAME, FontAwesome.BINOCULARS, true));
		verticalLayoutPage2.setSpacing(true);
		verticalLayoutPage2.setMargin(true);
		accordionMenu.addTab(verticalLayoutPage2, "Page2", FontAwesome.AMAZON);

		// NavigationBar
		final CssLayout navigationBar = new CssLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		// Create Navigation Buttons
		navigationBar.addComponent(createNavigationButton("Default View", DefaultView.VIEW_NAME, FontAwesome.ANDROID, false));
		navigationBar.addComponent(createNavigationButton("View Scoped View", ViewScopedView.VIEW_NAME, FontAwesome.ADN, false));
		navigationBar.addComponent(createNavigationButton("UI Scoped View", UIScopedView.VIEW_NAME, FontAwesome.ANGELLIST, false));
		navigationBar.addComponent(createNavigationButton("Restricted View", RestrictedView.VIEW_NAME, FontAwesome.PAINT_BRUSH, false));
		navigationBar.addComponent(createNavigationButton("Protected View", ProtectedView.VIEW_NAME, FontAwesome.BANK, false));
		navigationBar.addComponent(createNavigationButton("Access Denied View", AccessDeniedView.VIEW_NAME, FontAwesome.BARCODE, false));

		// SpringViewDisplay : View Container
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();

		// VerticalLayout (NavigationBar + SpringViewDisplay)
		final VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeFull();
		verticalLayout.setSpacing(true);
		verticalLayout.setMargin(true);
		verticalLayout.addComponent(navigationBar);
		verticalLayout.addComponent(springViewDisplay);
		verticalLayout.setExpandRatio(springViewDisplay, 1.0f);

		// Build Main Content
		final HorizontalLayout horizontalLayoutMainContent = new HorizontalLayout(accordionMenu, verticalLayout);
		horizontalLayoutMainContent.setSizeFull();
		// Configure the verticalLayoutMainContent to use all of the available space more efficiently.
		horizontalLayoutMainContent.setExpandRatio(verticalLayout, 1);

		// Set Content
		setContent(horizontalLayoutMainContent);

		// Post UI

		// Access Control and Error Views
		getNavigator().setErrorView(ErrorView.class);

		// With this Enabled to we are always sent to AccessDenied View, until we configure Spring Security
		// https://vaadin.com/api/vaadin-spring/com/vaadin/spring/navigator/SpringViewProvider.html
		springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
	}

	private Button createNavigationButton(String caption, final String viewName, Resource icon, boolean fullWidth) {

		Button button = new Button(caption);
		button.addStyleName(ValoTheme.BUTTON_SMALL);
		if (fullWidth) button.setWidth(100.0f, Unit.PERCENTAGE);
		//TODO: Style Butons
		//button.addStyleName(ValoTheme.BUTTON_PRIMARY);
		//if (icon.equals(null)) icon = FontAwesome.WINDOWS;
		//button.setIcon(icon);
		// If you didn't choose Java 8 when creating the project, convert this to an anonymous listener class
		button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
		return button;
	}
}
