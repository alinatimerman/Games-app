package controllerFX;

import model.WinterGame;
import model.Enrollment;
import service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ControllerFX {
    @FXML
    TableView<WinterGame> winterGameTableView;
    @FXML
    TableView<Enrollment> enrollmentTableView;
    @FXML
    TextField nameField, typeField, minAgeField, maxAgeField, childNameEField, parentNameEField, ageEField, winterGameEField;
    @FXML
    TextField searchChildField, searchWinterGameEField;
    @FXML
    Label idLabel, idELabel;
    @FXML
    CheckBox searchAllCheckBox, searchChildCheckBox, searchAllECheckBox, searchWinterGameECheckBox;
    @FXML
    DatePicker datePicker;

    private final ObservableList<WinterGame> winterGameList = FXCollections.observableArrayList();
    private final ObservableList<Enrollment> enrollmentList = FXCollections.observableArrayList();

    DatePickerConverter dateConverter = new DatePickerConverter();

    private Service service;

    public ControllerFX() {}

    @FXML
    public void initialize() {
        winterGameTableView.setItems(winterGameList);
        enrollmentTableView.setItems(enrollmentList);

        searchAllCheckBox.setSelected(true);
        searchAllECheckBox.setSelected(true);

        winterGameTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem)-> showWinterGame(newItem));
        enrollmentTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem)-> showEnrollment(newItem));

        datePicker.setConverter(dateConverter);
    }

    public void setService(Service service) {
        this.service = service;
        winterGameList.addAll(service.getWinterGameList());
        enrollmentList.addAll(service.getEnrollmentList());
        idLabel.setText("" + service.getWinterGameIdGenerator());
        idELabel.setText("" + service.getEnrollmentIdGenerator());
    }

//----------------------------------------------------WinterGame----------------------------------------------------//
    private void showWinterGame(WinterGame winterGame) {
        if (winterGame == null)
            clearFieldsWinterGame();
        else{
            idLabel.setText("" + winterGame.getId());
            nameField.setText(winterGame.getName());
            typeField.setText(winterGame.getType());
            minAgeField.setText("" + winterGame.getMinAge());
            maxAgeField.setText("" + winterGame.getMaxAge());
            datePicker.setValue(winterGame.getDate());
        }
    }

    @FXML
    private void clearFieldsWinterGame(){
        idLabel.setText("" + service.getWinterGameIdGenerator());
        nameField.setText("");
        typeField.setText("");
        minAgeField.setText("");
        maxAgeField.setText("");
        datePicker.setValue(null);
    }

    @FXML
    public void addWinterGameButton() {
        String name = nameField.getText();
        String type = typeField.getText();
        String minAgeS = minAgeField.getText();
        String maxAgeS = maxAgeField.getText();
        String dateS;

        try {
            dateS = datePicker.getValue().format(dateConverter.getDtFormatter());
        }catch(Exception ex){
            showErrorMessage("All fields must be completed");
            return;
        }

        if (name.equals("") || type.equals("") || maxAgeS.equals("") || minAgeS.equals("")) {
            showErrorMessage("All fields must be completed");
            return;
        }
        try {
            int minAge = Integer.parseInt(minAgeS);
            int maxAge = Integer.parseInt(maxAgeS);
            LocalDate date = LocalDate.parse(dateS, dateConverter.getDtFormatter());
            int id = service.addWinterGame(name, type, minAge, maxAge, date);
            winterGameList.setAll(service.getWinterGameList());
            idLabel.setText("" + (id + 1));
        } catch (Exception exception) {
            showNotification(exception.getMessage(), Alert.AlertType.ERROR);
        }

    }

//`````````````````````````````````````````````````Filter WinterGame`````````````````````````````````````````````````//
    @FXML
    public void showAllWinterGame(){
        searchAllCheckBox.setSelected(true);
        searchChildCheckBox.setSelected(false);
        winterGameList.setAll(service.getWinterGameList());
    }

    @FXML
    public void filterByChildWinterGame(){
        searchChildCheckBox.setSelected(true);
        searchAllCheckBox.setSelected(false);
        String child = searchChildField.getText();
        winterGameList.setAll(service.filterByChildWinterGame(child));
    }


//----------------------------------------------------Enrollment----------------------------------------------------//
    private void showEnrollment(Enrollment enrollment) {
        if (enrollment == null)
            clearFieldsEnrollment();
        else{
            idELabel.setText("" + enrollment.getId());
            childNameEField.setText(enrollment.getChildName());
            parentNameEField.setText(enrollment.getParentName());
            ageEField.setText("" + enrollment.getAge());
            winterGameEField.setText("" + enrollment.getWinterGame());
        }
    }

    @FXML
    private void clearFieldsEnrollment(){
        idELabel.setText("" + service.getEnrollmentIdGenerator());
        childNameEField.setText("");
        parentNameEField.setText("");
        ageEField.setText("");
        winterGameEField.setText("");
    }

    @FXML
    public void addEnrollmentButton(){
        String childName = childNameEField.getText();
        String parentName = parentNameEField.getText();
        String ageS = ageEField.getText();
        String winterGameS = winterGameEField.getText();


        if(childName.equals("") || parentName.equals("") || ageS.equals("") || winterGameS.equals("")){
            showErrorMessage("All fields must be completed");
            return;
        }
        try{
            int age = Integer.parseInt(ageS);
            int winterGame = Integer.parseInt(winterGameS);
            int id = service.addEnrollment(childName, parentName, age, winterGame);
            enrollmentList.setAll(service.getEnrollmentList());
            winterGameList.setAll(service.getWinterGameList());
            idELabel.setText("" + (id + 1));
        }catch(Exception exception){
            showNotification(exception.getMessage(), Alert.AlertType.ERROR);
        }
    }

//`````````````````````````````````````````````````Filter Enrollments`````````````````````````````````````````````````//
    @FXML
    public void showAllEnrollments(){
        searchAllECheckBox.setSelected(true);
        searchWinterGameECheckBox.setSelected(false);
        enrollmentList.setAll(service.getEnrollmentList());
    }

    @FXML
    public void filterByWinterGameEnrollments(){
        searchWinterGameECheckBox.setSelected(true);
        searchAllECheckBox.setSelected(false);
        String winterGameS = searchWinterGameEField.getText();
        int winterGame;
        if(winterGameS.equals("")){
            showErrorMessage("Winter game search field must be completed");
            return;
        }
        try{
            winterGame = Integer.parseInt(winterGameS);
        }catch (NumberFormatException exception){
            showErrorMessage("Introduce the id of the winter game");
            return;
        }
        enrollmentList.setAll(service.filterByWinterGameEnrollments(winterGame));
    }

//----------------------------------------------------ShowMessage----------------------------------------------------//
    void showErrorMessage (String text){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error message");
        message.setContentText(text);
        message.showAndWait();
    }

    private void showNotification(String message, Alert.AlertType type){
        Alert alert=new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
