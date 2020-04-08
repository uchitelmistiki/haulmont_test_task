package org.example;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.example.dbservice.DbProvider;
import org.example.uicomponents.Doctors;
import org.example.uicomponents.Patients;
import org.example.uicomponents.Recipes;

import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
    private Patients patientsComponents;
    private Doctors doctorsComponents;
    public static Recipes recipesComponents;


    private void initComponents(){
        final VerticalLayout layout = new VerticalLayout();
        final HorizontalLayout personsLayout = new HorizontalLayout();

        patientsComponents = new Patients();
        doctorsComponents = new Doctors();
        recipesComponents = new Recipes();
        recipesComponents.setWidthUndefined();

        personsLayout.addComponents(patientsComponents, doctorsComponents);
        personsLayout.setComponentAlignment(patientsComponents, Alignment.MIDDLE_LEFT);
        personsLayout.setComponentAlignment(doctorsComponents, Alignment.MIDDLE_RIGHT);

        layout.addComponent(personsLayout);
        layout.setComponentAlignment(personsLayout,Alignment.MIDDLE_CENTER);
        layout.addComponent(recipesComponents);
        layout.setComponentAlignment(recipesComponents,Alignment.MIDDLE_CENTER);

        setContent(layout);
    }
    private void initDB(){
        DbProvider.init();
    }

    @Override
    protected void init (VaadinRequest vaadinRequest){
        initDB();
        initComponents();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
