package org.example.uicomponents;

import com.vaadin.ui.*;
import org.example.MyUI;
import org.example.dbservice.dataSets.PatientDataSet;
import org.example.dbservice.dataproviders.PatientDataProvider;
import org.example.dbservice.dataproviders.RecipeDataProvider;
import org.example.validator.PatientValidator;
import java.util.List;

public class Patients extends Basic {

    private Grid patientsGrid;
    private List<PatientDataSet> patients;

    private Window addPatientSubWindow;
    private Window editPatientSubWindow;

    private TextField patientName;
    private TextField patientLastName;
    private TextField patientSecondName;
    private TextField patientsPhone;

    private Button addPatientButton;
    private Button deletePatientButton;
    private Button editPatientButton;
    private Button showEditPatientWindowButton;
    private Button showAddPatientWindowButton;

    private CssLayout patientMainButtonBox;

    private PatientDataProvider dataProvider;
    private RecipeDataProvider recipeDataProvider;

    public Patients(){
        initDataProviders();
        prepareButtons();
        initPatientsPrimaryContent();
    }

    // Конструктор для компонента с панелью кнопок,
    // предназначенных для выбора пациента при редактировании или добавления рецепта в компоненте Рецепты
    public Patients(CssLayout buttonBox){
        initDataProviders();
        initPatientsPrimaryContent(buttonBox);
    }

    private void initDataProviders(){
        recipeDataProvider = new RecipeDataProvider();
        dataProvider = new PatientDataProvider();
    }

    private void initTextFields(){
        patientName  = new TextField();
        patientName.setCaption("Имя:");

        patientLastName   = new TextField();
        patientLastName.setCaption("Фамилия:");

        patientSecondName  = new TextField();
        patientSecondName.setCaption("Отчество:");

        patientsPhone  = new TextField();
        patientsPhone.setCaption("Телефон:");
    }

    private void initPatientsPrimaryContent() {
        patients = dataProvider.getAllPatientsFromDB();
        patientsGrid = new Grid<PatientDataSet>();
        setItemClickListenerOnGrid(patientsGrid);
        createPatientTable(patientsGrid, patients);
        super.initPrimaryContent("Пациенты", patientsGrid, patientMainButtonBox);
        initTextFields();
    }

    private void initPatientsPrimaryContent(CssLayout buttonBox) {
        patients = dataProvider.getAllPatientsFromDB();
        patientsGrid = new Grid<PatientDataSet>();
        createPatientTable(patientsGrid, patients);
        super.initPrimaryContent("Пациенты", patientsGrid, buttonBox);
    }

    private void setItemClickListenerOnGrid(Grid grid){
        grid.addItemClickListener(e->{
        deletePatientButton.setEnabled(true);
         showEditPatientWindowButton.setEnabled(true);
        });
    }

    private void setDisableEditButton(){
        setDisableButton(showEditPatientWindowButton);
    }

    private void setDisableDeleteButton(){
        setDisableButton(deletePatientButton);
    }

    private void setDisableSelectedItemActionButtons(){
        setDisableEditButton();
        setDisableDeleteButton();
    }

    protected void prepareButtons() {
        super.prepareButtons();
        patientMainButtonBox = super.createMainButtonBox();
        patientMainButtonBox.addComponents(showEditPatientWindowButton, deletePatientButton, showAddPatientWindowButton);
    }

    private void clearPatientAddTextFields(){
        patientName.clear();
        patientLastName.clear();
        patientSecondName.clear();
        patientsPhone.clear();
    }

    private void createPatientTable(Grid<PatientDataSet> grid, List<PatientDataSet> patients){
        grid.setItems(patients);
        grid.addColumn(PatientDataSet::getPatientName).setCaption("Имя");
        grid.addColumn(PatientDataSet::getPatientLastName).setCaption("Фамилия");
        grid.addColumn(PatientDataSet::getPatientSecondName).setCaption("Отчество");
        grid.addColumn(PatientDataSet::getPatientsPhone).setCaption("Телефон");
    }

    private void createAddPatientSubWindow(){
        addPatientSubWindow = super.getBasicSubWindow("Добавить пациента");
        subContent.addComponents(patientName, patientLastName, patientSecondName, patientsPhone, addPatientButton);
        subContent.addComponent(Basic.createCancelButton(addPatientSubWindow, ()->clearPatientAddTextFields()));
        MyUI.getCurrent().addWindow(addPatientSubWindow);
    }

    private void createEditPatientSubWindow(PatientDataSet patientToEdit){
        editPatientSubWindow = super.getBasicSubWindow("Редактирование");
        subContent.addComponents(patientName, patientLastName, patientSecondName, patientsPhone, editPatientButton);
        setValuesToFieldsInEditSubWindow(patientToEdit);
        subContent.addComponent(Basic.createCancelButton(editPatientSubWindow,()->clearPatientAddTextFields()));
        MyUI.getCurrent().addWindow(editPatientSubWindow);
    }

   private void setValuesToFieldsInEditSubWindow(PatientDataSet patientToEdit){
       patientName.setValue(patientToEdit.getPatientName());
       patientLastName.setValue(patientToEdit.getPatientLastName());
       patientSecondName.setValue(patientToEdit.getPatientSecondName());
       patientsPhone.setValue(String.valueOf(patientToEdit.getPatientsPhone()));
   }

    @Override
    void createShowEditWindowButton() {
        showEditPatientWindowButton = new Button("Изменить");
        showEditPatientWindowButton.addClickListener(e -> {
            PatientDataSet selected = (PatientDataSet) getSelectedDataSet(patientsGrid);
            createEditPatientSubWindow(selected);
        });
        showEditPatientWindowButton.setEnabled(false);
    }

    @Override
    void createShowAddWindowButton() {
        showAddPatientWindowButton = new Button("Добавить");
        showAddPatientWindowButton.addClickListener(e -> {
            createAddPatientSubWindow();
        });
    }

    @Override
    void createEditDataSetButton() {
        editPatientButton = new Button("Ок");
        editPatientButton.addClickListener(e -> {

            PatientValidator patientValidator = getPatientValidator();
            String validateMessage = patientValidator.validatePatientFields();
            if(validateMessage.equals("Ok")){
                PatientDataSet selected = getSelectedPatient();
                dataProvider.updatePatientInDB(selected, patientValidator);
                refreshDataSetTable(patientsGrid, dataProvider.getAllPatientsFromDB());
                refreshDataSetTable(MyUI.recipesComponents, MyUI.recipesComponents.getRecipesGrid(), recipeDataProvider.getAllRecipesFromDB());
                MyUI.recipesComponents.applyFilters();
                editPatientSubWindow.close();
                clearPatientAddTextFields();
                setDisableSelectedItemActionButtons();
            }else{
                Notification.show(validateMessage);
            }
        });
    }

    @Override
    void createAddDataSetButton() {
        addPatientButton = new Button("Ок");
        addPatientButton.addClickListener(e -> {
            PatientValidator patientValidator = getPatientValidator();
            String validateMessage = patientValidator.validatePatientFields();
            if(validateMessage.equals("Ok")){
                dataProvider.addPatientToDB(patientValidator);
                refreshDataSetTable(patientsGrid, dataProvider.getAllPatientsFromDB());
                clearPatientAddTextFields();
                addPatientSubWindow.close();
                setSelectedToLastRow(patientsGrid);
            }else{
                Notification.show(validateMessage);
            }
        });
    }

    @Override
    void createDeleteDataSetButton() {
        deletePatientButton = new Button("Удалить");
        deletePatientButton.addClickListener(e -> {
            PatientDataSet selected =  getSelectedPatient();
            if(dataProvider.deletePatientFromDB(selected)) {
                refreshDataSetTable(patientsGrid, dataProvider.getAllPatientsFromDB());
                setDisableSelectedItemActionButtons();
            }else{
                Notification.show("Невозможно удалить запись - для данного пациента существует рецепт!");
            }
        });
        deletePatientButton.setEnabled(false);
    }

    private PatientValidator getPatientValidator(){
        PatientValidator patientValidator = new PatientValidator(
                patientName.getValue(),
                patientLastName.getValue(),
                patientSecondName.getValue(),
                patientsPhone.getValue()
        );
        return patientValidator;
    }

    public PatientDataSet getSelectedPatient(){
        PatientDataSet selected = (PatientDataSet) getSelectedDataSet(patientsGrid);
        return selected;
    }
}
