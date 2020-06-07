package com.library.visual.members;

import com.library.CURD.CRUDMembers;
import com.library.entity.Members;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class MembersScreenController {

    private CRUDMembers crudMembers;

    @FXML
    private TableView<Members> membersTable;
    @FXML
    private TextField idField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField emailField;
    @FXML
    private Button deleteMemberButton;
    @FXML
    private Button updateMemberButton;


    public void initialize() {

        crudMembers = new CRUDMembers();

        idField.setDisable(true);


        //populate table with member info
        membersTable.setItems(getMembers());

        //when clicked on a table row, populate text fields
        membersTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Members>() {
            @Override
            public void changed(ObservableValue<? extends Members> observableValue, Members members, Members newValue) {
                if (newValue != null) {
                    Members member = membersTable.getSelectionModel().getSelectedItem();
                    idField.setText(Integer.toString(member.getId()));
                    firstNameField.setText(member.getFirstName());
                    lastNameField.setText(member.getLastName());
                    addressField.setText(member.getAddress());
                    phoneNumberField.setText(member.getPhoneNum());
                    emailField.setText(member.getEmail());
                    // if a row is clicked enable update member button
                    if(member != null){
                        updateMemberButton.setDisable(false);
                    }else{
                        updateMemberButton.setDisable(true);
                    }
                    //if a member has'n books on loan deleting is enabled
                    if(crudMembers.memberHasBooks(member)){
                        deleteMemberButton.setDisable(false);
                    }else{
                        deleteMemberButton.setDisable(true);
                    }
                }
            }
        });
        //only one row can be selected
        membersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //initial state of update and delete buttons
        updateMemberButton.setDisable(true);
        deleteMemberButton.setDisable(true);
    }

    //get members from DB
    private ObservableList<Members> getMembers() {
        List<Members> membersList = crudMembers.getAllMembers();

        ObservableList<Members> membersListObs = FXCollections.observableArrayList(membersList);

        return membersListObs;

    }

    //add new member to db
    @FXML
    private void addMember() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(membersTable.getScene().getWindow());
        dialog.setTitle("Add new member");
        dialog.setHeaderText("Use this dialog to create new member");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addmemberdialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddMemberController controller = fxmlLoader.getController();
            Members newMember = controller.addMember();
            crudMembers.addMember(newMember);
            membersTable.setItems(getMembers());
        }
        membersTable.getSelectionModel().selectFirst();
    }

    //delete a member
    public void deleteMember(){
        Members member = membersTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Delete Contact");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected contact: "+member.getFirstName()+" "+member.getLastName());

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK){
            crudMembers.deleteMember(member);
            membersTable.setItems(getMembers());
            idField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            addressField.setText("");
            phoneNumberField.setText("");
            emailField.setText("");
        }
        membersTable.getSelectionModel().selectFirst();
    }

    //update members info
    public void updateMember(){
        Members member = membersTable.getSelectionModel().getSelectedItem();
        int id = member.getId();
        String firstName= firstNameField.getText();
        String lastName= lastNameField.getText();
        String address= addressField.getText();
        String phoneNum= phoneNumberField.getText();
        String email= emailField.getText();

        crudMembers.updateMember(id, firstName, lastName, address, phoneNum, email);
        membersTable.setItems(getMembers());
    }

    //goto main screen
    @FXML
    public void gotoMain(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../main/mainscreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Library Management");
            stage.setScene(new Scene(root, 1500, 900));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


