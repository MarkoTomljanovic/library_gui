package com.library.visual.members;

import com.library.entity.Members;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AddMemberController {

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

    //creating a new member
    public Members addMember(){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String address = addressField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim();
        String email = emailField.getText().trim();

        Members newMember = new Members(firstName, lastName, address, phoneNumber, email);
        return newMember;
    }


}
