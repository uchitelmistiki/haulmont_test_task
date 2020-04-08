package org.example.uicomponents;

import com.vaadin.ui.*;
import org.example.dbservice.dataSets.DataSet;
import java.util.Collection;
import java.util.List;

public abstract class Basic extends CustomComponent {

    public interface Closable{
         void doWithClose();
    }

    abstract void createShowEditWindowButton();
    abstract void createShowAddWindowButton();
    abstract void createEditDataSetButton();
    abstract void createAddDataSetButton();
    abstract void createDeleteDataSetButton();

    protected Window subWindow;
    protected VerticalLayout subContent;

    protected void prepareButtons(){
        createShowEditWindowButton();
        createShowAddWindowButton();
        createEditDataSetButton();
        createAddDataSetButton();
        createDeleteDataSetButton();
    }

    public static Button createCancelButton(Window window){
        Button cancelButton = new Button("Отмена");
        cancelButton.addClickListener(e->{
            window.close();
        });
        return cancelButton;
    }

    public static Button createCancelButton(Window window, Closable closable){
        Button cancelButton = new Button("Отмена");
        cancelButton.addClickListener(e->{
            window.close();
            closable.doWithClose();
        });
        return cancelButton;
    }

    protected CssLayout createMainButtonBox(){
        Panel mainButtonBoxPanel = new Panel();
        CssLayout mainButtonBox = new CssLayout();
        mainButtonBox.setSizeUndefined();
        mainButtonBoxPanel.setContent(mainButtonBox);
        mainButtonBoxPanel.setSizeUndefined();
        return mainButtonBox;
    }

    //Создание панели для кнопки выбора связанных сущностей и отображения последующего результата выбора
    protected HorizontalLayout createLayoutFormSelect(TextField selectedItemField, Button button){
        Panel selectFormPanel = new Panel();
        HorizontalLayout selectFormLayout = new HorizontalLayout();
        selectedItemField.setEnabled(false);
        selectFormLayout.addComponents(selectedItemField,button);
        selectFormLayout.setComponentAlignment(selectedItemField, Alignment.MIDDLE_LEFT);
        selectFormLayout.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
        selectFormPanel.setContent(selectFormLayout);
        return selectFormLayout;
    }


    protected void initPrimaryContent(String caption, Grid grid, CssLayout buttonBox){
        Panel primaryPanel = new Panel(caption);
        VerticalLayout primaryContent = new VerticalLayout();
        primaryPanel.setContent(primaryContent);
        setCompositionRoot(primaryPanel);
        primaryPanel.setSizeUndefined();
        primaryContent.setSizeUndefined();
        primaryContent.addComponent(grid);
        primaryContent.addComponent(buttonBox);
    }

    protected void initPrimaryContent(String caption, Grid grid, CssLayout buttonBox, HorizontalLayout filtersForm){
        Panel primaryPanel = new Panel(caption);
        VerticalLayout primaryContent = new VerticalLayout();
        primaryPanel.setContent(primaryContent);
        setCompositionRoot(primaryPanel);
        primaryPanel.setSizeUndefined();
        primaryContent.setSizeUndefined();
        primaryContent.addComponent(filtersForm);
        primaryContent.addComponent(grid);
        primaryContent.addComponent(buttonBox);

    }

    protected void refreshDataSetTable(Grid<DataSet> grid, List<? extends DataSet> dataSets){
        grid.setItems((Collection<DataSet>) dataSets);
    }

    // Обновление таблицы из одного компонента в другом. Используется при изменении пациента или врача,
    // при этом пациент или врач так же изменяют свои данные в таблице Рецепты
    protected void refreshDataSetTable(Basic component, Grid<DataSet> grid, List<? extends DataSet> dataSets){
        component.refreshDataSetTable(grid, dataSets);
    }

    protected void setSelectedToLastRow(Grid grid){
        grid.select(grid.getDataCommunicator().
                fetchItemsWithRange(grid.getDataCommunicator().
                        getDataProviderSize()-1,1).
                iterator().next()
        );
    }

    protected DataSet getSelectedDataSet(Grid<DataSet> grid){
        DataSet ds = grid.getSelectionModel().getFirstSelectedItem().get();
        return ds;
    }

    protected Window getBasicSubWindow(String caption){
        subWindow = new Window(caption);
        subWindow.setModal(true);
        subWindow.setClosable(false);
        subWindow.center();
        subContent = new VerticalLayout();
        subWindow.setContent(subContent);
        return subWindow;
    }
    //Проверка на содержание текста в поле. Используется в фильтре.
    protected Boolean caseInsensitiveContains(String where, String what) {
        return where.toLowerCase().contains(what.toLowerCase());
    }

    protected HorizontalLayout createLayoutFilters(HorizontalLayout fields, Button button){
        Panel filtersPanel = new Panel("Фильтры");
        fields.addComponent(button);
        fields.setWidthUndefined();
        fields.setComponentAlignment(button, Alignment.MIDDLE_RIGHT);
        filtersPanel.setContent(fields);
        return fields;
    }

    protected void setDisableButton(Button button){
        button.setEnabled(false);
    }
}
