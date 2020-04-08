package org.example.uicomponents;

import com.vaadin.ui.*;
import org.example.MyUI;
import org.example.dbservice.dataSets.DoctorDataSet;
import org.example.dbservice.dataSets.DoctorStatDataSet;
import org.example.dbservice.dataproviders.DoctorDataProvider;
import org.example.dbservice.dataproviders.RecipeDataProvider;
import org.example.validator.DoctorValidator;
import java.util.List;

public class Doctors extends Basic {
    private Grid doctorsGrid;
    private Grid doctorStatGrid;
    private List<DoctorDataSet> doctors;

    private Window addDoctorSubWindow;
    private Window editDoctorSubWindow;
    private Window doctorStatSubWindow;

    private TextField doctorName;
    private TextField doctorLastName;
    private TextField doctorSecondName;
    private TextField doctorSpecialization;

    private Button addDoctorButton;
    private Button deleteDoctorButton;
    private Button editDoctorButton;
    private Button showEditDoctorWindowButton;
    private Button showAddDoctorWindowButton;
    private Button showRecipeStatistics;

    private CssLayout doctorMainButtonBox;

    private DoctorDataProvider dataProvider;
    private RecipeDataProvider recipeDataProvider;


    public Doctors(){
        initDataProviders();
        prepareButtons();
        initDoctorsPrimaryContent();
    }

    public Doctors(CssLayout buttonBox){
        initDataProviders();
        initDoctorsPrimaryContent(buttonBox);
    }

    private void initDataProviders(){
        recipeDataProvider = new RecipeDataProvider();
        dataProvider = new DoctorDataProvider();
    }

    private void initTextFields(){
        doctorName  = new TextField();
        doctorName.setCaption("Имя:");

        doctorLastName   = new TextField();
        doctorLastName.setCaption("Фамилия:");

        doctorSecondName  = new TextField();
        doctorSecondName.setCaption("Отчество:");

        doctorSpecialization  = new TextField();
        doctorSpecialization.setCaption("Специализация:");
    }

    private void initDoctorsPrimaryContent(){
        doctors = dataProvider.getAllDoctorsFromDB();
        doctorsGrid = new Grid<DoctorDataSet>();
        setItemClickListenerOnGrid(doctorsGrid);
        createDoctorTable(doctorsGrid, doctors);
        super.initPrimaryContent("Врачи", doctorsGrid, doctorMainButtonBox);
        initTextFields();
    }

    private void initDoctorsPrimaryContent(CssLayout buttonBox){
        doctors = dataProvider.getAllDoctorsFromDB();
        doctorsGrid = new Grid<DoctorDataSet>();
        createDoctorTable(doctorsGrid, doctors);
        super.initPrimaryContent("Врачи", doctorsGrid, buttonBox);
    }

    private void setItemClickListenerOnGrid(Grid grid){
        grid.addItemClickListener(e->{
            deleteDoctorButton.setEnabled(true);
            showEditDoctorWindowButton.setEnabled(true);
            showRecipeStatistics.setEnabled(true);
        });
    }


    private void clearDoctorAddTextFields(){
        doctorName.clear();
        doctorLastName.clear();
        doctorSecondName.clear();
        doctorSpecialization.clear();
    }

    private void setDisableEditButton(){
        setDisableButton(showEditDoctorWindowButton);
    }

    private void setDisableDeleteButton(){
        setDisableButton(deleteDoctorButton);
    }

    private void setDisableShowStatButton(){
        setDisableButton(showRecipeStatistics);
    }

    private void setDisableSelectedItemActionButtons(){
        setDisableEditButton();
        setDisableDeleteButton();
        setDisableShowStatButton();
    }

    private void createDoctorTable(Grid<DoctorDataSet> grid, List<DoctorDataSet> doctors){
        grid.setItems(doctors);
        grid.addColumn(DoctorDataSet::getDoctorName).setCaption("Имя");
        grid.addColumn(DoctorDataSet::getDoctorLastName).setCaption("Фамилия");
        grid.addColumn(DoctorDataSet::getDoctorSecondName).setCaption("Отчество");
        grid.addColumn(DoctorDataSet::getDoctorSpecialization).setCaption("Специализация");
    }

    private void createDoctorStatTable(Grid<DoctorStatDataSet> grid, List<DoctorStatDataSet> doctors){
        grid.setItems(doctors);
        grid.addColumn(DoctorStatDataSet::getGroupDateCreateRecipe).setCaption("Дата");
        grid.addColumn(DoctorStatDataSet::getCountRecipe).setCaption("Количество рецептов");
    }

    private void createAddDoctorSubWindow(){
        addDoctorSubWindow = super.getBasicSubWindow("Добавить врача");
        subContent.addComponents(doctorName, doctorLastName, doctorSecondName, doctorSpecialization, addDoctorButton);
        subContent.addComponent(Basic.createCancelButton(addDoctorSubWindow,()->clearDoctorAddTextFields()));
        MyUI.getCurrent().addWindow(addDoctorSubWindow);
    }

    private void createRecipeStatisticsWindow(DoctorDataSet selected){
        if(selected!=null) {
            doctorStatSubWindow = super.getBasicSubWindow("Статистика - "+selected.getFormattedDoctorName());
            List<DoctorStatDataSet> stat = dataProvider.getDoctorStatByIdFromDB(selected.getDoctorId());
            doctorStatGrid = new Grid<DoctorStatDataSet>();
            createDoctorStatTable(doctorStatGrid, stat);
            subContent.addComponent(doctorStatGrid);
            subContent.addComponent(new Label("Всего: " + dataProvider.getCountOfDoctorStatByIdFromDB(selected.getDoctorId())));
            subContent.addComponent(Basic.createCancelButton(doctorStatSubWindow));
            MyUI.getCurrent().addWindow(doctorStatSubWindow);
        }
    }

    private void createEditDoctorSubWindow(DoctorDataSet doctorToEdit){
        editDoctorSubWindow = super.getBasicSubWindow("Редактирование");
        subContent.addComponents(doctorName, doctorLastName, doctorSecondName, doctorSpecialization, editDoctorButton);
        setValuesToFieldsInEditSubWindow(doctorToEdit);
        subContent.addComponent(Basic.createCancelButton(editDoctorSubWindow,()->clearDoctorAddTextFields()));
        MyUI.getCurrent().addWindow(editDoctorSubWindow);
    }

    private void setValuesToFieldsInEditSubWindow(DoctorDataSet doctorToEdit){
        doctorName.setValue(doctorToEdit.getDoctorName());
        doctorLastName.setValue(doctorToEdit.getDoctorLastName());
        doctorSecondName.setValue(doctorToEdit.getDoctorSecondName());
        doctorSpecialization.setValue(doctorToEdit.getDoctorSpecialization());
    }

    protected void prepareButtons() {
        super.prepareButtons();
        createShowRecipeStatisticsButton();
        doctorMainButtonBox = super.createMainButtonBox();
        doctorMainButtonBox.addComponents(showEditDoctorWindowButton, deleteDoctorButton, showAddDoctorWindowButton, showRecipeStatistics);
    }

    @Override
    void createShowEditWindowButton() {
        showEditDoctorWindowButton = new Button("Изменить");
        showEditDoctorWindowButton.addClickListener(e -> {
            DoctorDataSet selected = (DoctorDataSet) getSelectedDataSet(doctorsGrid);
            createEditDoctorSubWindow(selected);
        });
        showEditDoctorWindowButton.setEnabled(false);
    }

    @Override
    void createShowAddWindowButton() {
        showAddDoctorWindowButton = new Button("Добавить");
        showAddDoctorWindowButton.addClickListener(e -> {
            createAddDoctorSubWindow();
        });
    }

    void createShowRecipeStatisticsButton(){
        showRecipeStatistics = new Button("Показать статистику");
        showRecipeStatistics.addClickListener(e->{
            DoctorDataSet selected = getSelectedDoctor();
            createRecipeStatisticsWindow(selected);
        });
        showRecipeStatistics.setEnabled(false);
    }

    @Override
    void createEditDataSetButton() {
        editDoctorButton = new Button("Ок");
        editDoctorButton.addClickListener(e -> {
            DoctorValidator doctorValidator = getDoctorValidator();
            String validateMessage = doctorValidator.validateDoctorFields();
            if(validateMessage.equals("Ok")) {
                DoctorDataSet selected = getSelectedDoctor();
                dataProvider.updateDoctorInDB(selected, doctorValidator);
                refreshDataSetTable(doctorsGrid,dataProvider.getAllDoctorsFromDB());
                refreshDataSetTable(MyUI.recipesComponents, MyUI.recipesComponents.getRecipesGrid(), recipeDataProvider.getAllRecipesFromDB());
                MyUI.recipesComponents.applyFilters();
                editDoctorSubWindow.close();
                clearDoctorAddTextFields();
                setDisableSelectedItemActionButtons();
            }else{
                Notification.show(validateMessage);
            }
        });

    }

    @Override
    void createAddDataSetButton() {
        addDoctorButton = new Button("Ок");
        addDoctorButton.addClickListener(e -> {
            DoctorValidator doctorValidator = getDoctorValidator();
            String validateMessage = doctorValidator.validateDoctorFields();
            if(validateMessage.equals("Ok")){
                dataProvider.addDoctorToDB(doctorValidator);
                refreshDataSetTable(doctorsGrid, dataProvider.getAllDoctorsFromDB());
                clearDoctorAddTextFields();
                addDoctorSubWindow.close();
                setSelectedToLastRow(doctorsGrid);
            }else{
                Notification.show(validateMessage);
            }
        });
    }

    @Override
    void createDeleteDataSetButton() {
        deleteDoctorButton = new Button("Удалить");
        deleteDoctorButton.addClickListener(e -> {
            DoctorDataSet selected = getSelectedDoctor();
            if(dataProvider.deleteDoctorFromDB(selected)){
                refreshDataSetTable(doctorsGrid, dataProvider.getAllDoctorsFromDB());
                setDisableSelectedItemActionButtons();
            }else{
                Notification.show("Невозможно удалить запись - у выбранного врача существует выписанный рецепт!");
            }
        });
        deleteDoctorButton.setEnabled(false);
    }

    public DoctorDataSet getSelectedDoctor(){
        DoctorDataSet selected = (DoctorDataSet) getSelectedDataSet(doctorsGrid);
        return selected;
    }

    private DoctorValidator getDoctorValidator(){
        DoctorValidator doctorValidator = new DoctorValidator(
                doctorName.getValue(),
                doctorLastName.getValue(),
                doctorSecondName.getValue(),
                doctorSpecialization.getValue()
        );
        return doctorValidator;
    }
}
