package org.example.uicomponents;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import org.example.MyUI;
import org.example.dbservice.dataSets.DoctorDataSet;
import org.example.dbservice.dataSets.PatientDataSet;
import org.example.dbservice.dataSets.RecipeDataSet;
import org.example.dbservice.dataproviders.DoctorDataProvider;
import org.example.dbservice.dataproviders.PatientDataProvider;
import org.example.dbservice.dataproviders.RecipeDataProvider;
import org.example.validator.RecipeValidator;
import java.util.ArrayList;
import java.util.List;

public class Recipes extends Basic {

    public Grid recipesGrid;
    private List<RecipeDataSet> recipes;

    private Window addRecipeSubWindow;
    private Window editRecipeSubWindow;

    //Окна для выбора пациента и доктора при создании или редактировании рецепта
    private Window selectPatientSubWindow;
    private Window selectDoctorSubWindow;

    //Текстовые поля для хранения пациента и доктора в форме выбора при создании или редактировании рецепта
    private TextField selectedPatientField;
    private TextField selectedDoctorField;

    private TextArea recipeDescription;
    private DateField recipeDateOfCreation;
    private DateField recipeValidity;
    private NativeSelect recipePriority;

    //Кнопки для подтверждения добавления,изменения или удаления рецепта
    private Button addRecipeButton;
    private Button deleteRecipeButton;
    private Button editRecipeButton;

    //Кнопки для вызова формы добавления и редактрования рецепта
    private Button showEditRecipeWindowButton;
    private Button showAddRecipeWindowButton;

    //Кнопки для вызова формы выбора пациента и врача
    private Button showSelectPatientWindowButton;
    private Button showSelectDoctorWindowButton;

    //Кнопки для подтверждения выбора пациента и врача
    private Button selectPatientButton;
    private Button selectDoctorButton;

    //Поля для сохранения выбранного врача и пациента
    private PatientDataSet selectedPatientDataSet;
    private DoctorDataSet selectedDoctorDataSet;

    private CssLayout recipeMainButtonBox;

    //Панель и поля атрибутов фильтра
    private HorizontalLayout filters;
    private TextField recipeDescriptionFilter;
    private NativeSelect recipePriorityFilter;
    private NativeSelect patientListFilter;

    //Поставщики данных для рецептов,пациентов и врачей
    private RecipeDataProvider recipeDataProvider;
    private PatientDataProvider patientDataProvider;
    private DoctorDataProvider doctorDataProvider;

    public Recipes(){
        initDataProviders();
        prepareButtons();
        initRecipesPrimaryContent();
    }

    private void initDataProviders(){
        recipeDataProvider = new RecipeDataProvider();
        patientDataProvider = new PatientDataProvider();
        doctorDataProvider = new DoctorDataProvider();
    }

    private void initFields(){
        recipeDescription = new TextArea();
        recipeDescription.setCaption("Описание");
        recipeDescription.setWidth("100%");

        selectedPatientField = new TextField();
        selectedPatientField.setPlaceholder("Выбрать пациента");

        selectedDoctorField = new TextField();
        selectedDoctorField.setPlaceholder("Выбрать врача");

        recipeDateOfCreation = new DateField();
        recipeDateOfCreation.setCaption("Дата создания");
        recipeDateOfCreation.setDateFormat("yyyy-MM-dd");
        recipeDateOfCreation.setTextFieldEnabled(false);
        recipeDateOfCreation.addValueChangeListener(e->{
            recipeValidity.setRangeStart(recipeDateOfCreation.getValue());
        });

        recipeValidity = new DateField();
        recipeValidity.setCaption("Срок действия");
        recipeValidity.setDateFormat("yyyy-MM-dd");
        recipeValidity.setTextFieldEnabled(false);

        recipePriority = new NativeSelect<String>("Приоритет");
        recipePriority.setItems("Нормальный", "Cito", "Statim");
        recipePriority.setEmptySelectionAllowed(false);
        recipePriority.setSelectedItem("Нормальный");

        //Инициализация полей фильтра
        recipePriorityFilter = new NativeSelect<String>("Приоритет");
        recipePriorityFilter.setItems("Нормальный", "Cito", "Statim");

        recipeDescriptionFilter = new TextField();
        recipeDescriptionFilter.setPlaceholder("Описание");

        patientListFilter = new NativeSelect<String>("Пациент");
        List<String> patientsToFilter = new ArrayList<>();
        List<PatientDataSet> patients = patientDataProvider.getAllPatientsFromDB();
        for (PatientDataSet patient : patients) {
            patientsToFilter.add(patient.getFormattedPatientName());
        }
        patientListFilter.setItems(patientsToFilter);

    }

    private void initRecipeGrid(){
        recipes = recipeDataProvider.getAllRecipesFromDB();
        recipesGrid = new Grid<RecipeDataSet>();
        recipesGrid.setWidth("1038px");
        createRecipeTable(recipesGrid, recipes);
    }

    private void initRecipesPrimaryContent(){
        initFields();
        initFilters();
        initRecipeGrid();
        setItemClickListenerOnGrid(recipesGrid);
        super.initPrimaryContent("Рецепты", recipesGrid, recipeMainButtonBox, filters);
    }

    //Установка полей в панель филтьра и слушателя на кнопку Применить
    private void initFilters(){
        filters = new HorizontalLayout();
        Button acceptFilter =  new Button("Применить", e->{
            applyFilters();
            setDisableSelectedItemActionButtons();
        });
        filters.addComponent(recipeDescriptionFilter);
        filters.setComponentAlignment(recipeDescriptionFilter, Alignment.BOTTOM_CENTER);
        filters.addComponent(patientListFilter);
        filters.setComponentAlignment(patientListFilter, Alignment.BOTTOM_CENTER);
        filters.addComponent(recipePriorityFilter);
        filters = createLayoutFilters(filters, acceptFilter);
    }

    //Применить фильтры
    public void applyFilters(){
        ListDataProvider<RecipeDataSet> dataProvider = (ListDataProvider<RecipeDataSet>)recipesGrid.getDataProvider();
        dataProvider.setFilter(RecipeDataSet::getRecipeDescription,s->caseInsensitiveContains(s, recipeDescriptionFilter.getValue()));
        if(recipePriorityFilter.getValue()!=null){
            dataProvider.addFilter(RecipeDataSet::getRecipePriority,s->caseInsensitiveContains(s, recipePriorityFilter.getValue().toString()));
        }
        if(patientListFilter.getValue()!=null){
            dataProvider.addFilter(RecipeDataSet::getPatientInRecipe,s->caseInsensitiveContains(s, patientListFilter.getValue().toString()));
        }
    }

    //Установка слушателя - активация кнопок Удалить и Редактировать
    private void setItemClickListenerOnGrid(Grid grid){
        grid.addItemClickListener(e->{
            deleteRecipeButton.setEnabled(true);
            showEditRecipeWindowButton.setEnabled(true);
        });
    }

    private void setDisableEditButton(){
        setDisableButton(showEditRecipeWindowButton);
    }

    private void setDisableDeleteButton(){
        setDisableButton(deleteRecipeButton);
    }

    //Деактивация кнопок
    private void setDisableSelectedItemActionButtons(){
        setDisableEditButton();
        setDisableDeleteButton();
    }

    private Patients createFormToSelectPatient(){
        CssLayout cs = super.createMainButtonBox();
        Patients listPatientsToSelect = new Patients(cs);
        selectPatientButton = new Button("Ок");
        selectPatientButton.addClickListener(e->{
            selectedPatientDataSet = listPatientsToSelect.getSelectedPatient();
            selectedPatientField.setValue(selectedPatientDataSet.getFormattedPatientName());
            selectPatientSubWindow.close();
        });
        cs.addComponent(Basic.createCancelButton(selectPatientSubWindow));
        cs.addComponent(selectPatientButton);

        return listPatientsToSelect;
    }

    private Doctors createFormToSelectDoctors(){
        CssLayout cs = super.createMainButtonBox();
        Doctors listDoctorsToSelect = new Doctors(cs);
        selectDoctorButton = new Button("Ок");
        selectDoctorButton.addClickListener(e->{
            selectedDoctorDataSet = listDoctorsToSelect.getSelectedDoctor();
            selectedDoctorField.setValue(selectedDoctorDataSet.getFormattedDoctorName());
            selectDoctorSubWindow.close();
        });
        cs.addComponent(Basic.createCancelButton(selectDoctorSubWindow));
        cs.addComponent(selectDoctorButton);

        return listDoctorsToSelect;
    }

    private void createRecipeTable(Grid<RecipeDataSet> grid, List<RecipeDataSet> recipes){
        grid.setItems(recipes);
        grid.addColumn(RecipeDataSet::getRecipeDescription).setCaption("Описание").setWidth(200);
        grid.addColumn(RecipeDataSet::getPatientInRecipe).setCaption("Пациент").setWidth(200);
        grid.addColumn(RecipeDataSet::getDoctorInRecipe).setCaption("Врач").setWidth(200);
        grid.addColumn(RecipeDataSet::getRecipeDateOfCreation).setCaption("Дата создания");
        grid.addColumn(RecipeDataSet::getRecipeValidity).setCaption("Срок действия");
        grid.addColumn(RecipeDataSet::getRecipePriority).setCaption("Приоритет");
    }

    protected void prepareButtons() {
        super.prepareButtons();
        createShowSelectDoctorButton();
        createShowSelectPatientButton();
        recipeMainButtonBox = super.createMainButtonBox();
        recipeMainButtonBox.addComponents(showEditRecipeWindowButton, deleteRecipeButton, showAddRecipeWindowButton);
    }

    private void clearRecipeAddTextFields(){
        recipeDescription.clear();
        selectedPatientField.clear();
        selectedDoctorField.clear();
        recipeDateOfCreation.clear();
        recipeValidity.clear();
        recipePriority.setSelectedItem("Нормальный");
    }

    void createAddRecipeSubWindow(){
        addRecipeSubWindow = super.getBasicSubWindow("Добавить рецепт");
        subContent.addComponents(
                recipeDescription,
                super.createLayoutFormSelect(selectedPatientField, showSelectPatientWindowButton),
                super.createLayoutFormSelect(selectedDoctorField, showSelectDoctorWindowButton),
                recipeDateOfCreation,
                recipeValidity,
                recipePriority,
                addRecipeButton,
                Basic.createCancelButton(addRecipeSubWindow,()->clearRecipeAddTextFields())
        );

        MyUI.getCurrent().addWindow(addRecipeSubWindow);
    }

    void createEditRecipeSubWindow(RecipeDataSet recipeToEdit){
        editRecipeSubWindow = super.getBasicSubWindow("Изменить рецепт");
        subContent.addComponents(
                recipeDescription,
                super.createLayoutFormSelect(selectedPatientField, showSelectPatientWindowButton),
                super.createLayoutFormSelect(selectedDoctorField, showSelectDoctorWindowButton),
                recipeDateOfCreation,
                recipeValidity,
                recipePriority,
                editRecipeButton,
                Basic.createCancelButton(editRecipeSubWindow,()->clearRecipeAddTextFields())
        );
        setValuesToFieldsInEditSubWindow(recipeToEdit);
        MyUI.getCurrent().addWindow(editRecipeSubWindow);
    }

    void setValuesToFieldsInEditSubWindow(RecipeDataSet recipeToEdit){
        recipeDescription.setValue(recipeToEdit.getRecipeDescription());
        selectedPatientField.setValue(recipeToEdit.getPatientInRecipe());
        selectedDoctorField.setValue(recipeToEdit.getDoctorInRecipe());
        recipeDateOfCreation.setValue(recipeToEdit.getRecipeDateOfCreation().toLocalDate());
        recipeValidity.setValue(recipeToEdit.getRecipeValidity().toLocalDate());
        recipePriority.setValue(recipeToEdit.getRecipePriority());
        selectedPatientDataSet = patientDataProvider.getPatientByIdFromDB(recipeToEdit.getPatientInRecipeId());
        selectedDoctorDataSet = doctorDataProvider.getDoctorByIdFromDB(recipeToEdit.getDoctorInRecipeId());
    }

    @Override
    void createShowEditWindowButton() {
        showEditRecipeWindowButton = new Button("Изменить");
        showEditRecipeWindowButton.addClickListener(e->{
            RecipeDataSet selected = (RecipeDataSet) getSelectedDataSet(recipesGrid);
            createEditRecipeSubWindow(selected);
        });
        showEditRecipeWindowButton.setEnabled(false);
    }

    @Override
    void createShowAddWindowButton() {
        showAddRecipeWindowButton = new Button("Добавить");
        showAddRecipeWindowButton.addClickListener(e -> {
            createAddRecipeSubWindow();
        });
    }

    @Override
    void createEditDataSetButton() {
        editRecipeButton = new Button("Ок");
        editRecipeButton.addClickListener(e->{
            RecipeValidator recipeValidator = getRecipeValidator();
            String message = recipeValidator.validateRecipeFields();
            if(message.equals("Ok")) {
                RecipeDataSet selected = getSelectedRecipe();
                recipeValidator.setRecipeDoctorId(selectedDoctorDataSet.getDoctorId());
                recipeValidator.setRecipePatientId(selectedPatientDataSet.getPatientId());
                recipeDataProvider.updateRecipeInDB(selected, recipeValidator);
                refreshDataSetTable(recipesGrid, recipeDataProvider.getAllRecipesFromDB());
                applyFilters();
                editRecipeSubWindow.close();
                clearRecipeAddTextFields();
                setDisableSelectedItemActionButtons();
            }else{
                Notification.show(message);
            }
        });
    }

    @Override
    void createAddDataSetButton() {
        addRecipeButton = new Button("Ок");
        addRecipeButton.addClickListener(e->{
            RecipeValidator recipeValidator = getRecipeValidator();
            String message = recipeValidator.validateRecipeFields();
            if(message.equals("Ok")) {
                recipeValidator.setRecipeDoctorId(selectedDoctorDataSet.getDoctorId());
                recipeValidator.setRecipePatientId(selectedPatientDataSet.getPatientId());
                recipeDataProvider.addRecipeToDB(recipeValidator);
                refreshDataSetTable(recipesGrid, recipeDataProvider.getAllRecipesFromDB());
                applyFilters();
                addRecipeSubWindow.close();
                setSelectedToLastRow(recipesGrid);
            }else{
                Notification.show(message);
            }
        });
    }

    @Override
    void createDeleteDataSetButton() {
        deleteRecipeButton = new Button("Удалить");
        deleteRecipeButton.addClickListener(e->{
            RecipeDataSet selected = getSelectedRecipe();
            recipeDataProvider.deleteRecipeFromDB(selected);
            refreshDataSetTable(recipesGrid, recipeDataProvider.getAllRecipesFromDB());
            applyFilters();
            setDisableSelectedItemActionButtons();
        });
        deleteRecipeButton.setEnabled(false);
    }


    void createShowSelectPatientButton() {
        showSelectPatientWindowButton = new Button("...");
        showSelectPatientWindowButton.addClickListener(e -> {
            selectPatientSubWindow = super.getBasicSubWindow("Выбрать пациента");
            subContent.addComponent(createFormToSelectPatient());
            MyUI.getCurrent().addWindow(selectPatientSubWindow);
        });
    }

    void createShowSelectDoctorButton() {
        showSelectDoctorWindowButton = new Button("...");
        showSelectDoctorWindowButton.addClickListener(e -> {
            selectDoctorSubWindow = super.getBasicSubWindow("Выбрать врача");
            subContent.addComponent(createFormToSelectDoctors());
            MyUI.getCurrent().addWindow(selectDoctorSubWindow);
        });
    }

    //Получение проверщика(валидатора) для набора данных сущности Рецепты
    private RecipeValidator getRecipeValidator(){
        RecipeValidator recipeValidator = new RecipeValidator(
                recipeDescription.getValue(),
                selectedPatientField.getValue(),
                selectedDoctorField.getValue(),
                recipeDateOfCreation.getValue(),
                recipeValidity.getValue(),
                recipePriority.getValue().toString()
        );
        return recipeValidator;
    }

    public RecipeDataSet getSelectedRecipe(){
        RecipeDataSet selected = (RecipeDataSet) getSelectedDataSet(recipesGrid);
        return selected;
    }

    public Grid getRecipesGrid(){
        return this.recipesGrid;
    }
}
