package com.library.visual.books;

import com.library.entity.Books;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


public class AddBookController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorsLastNameField;
    @FXML
    private TextField authorsFirstNameField;
    @FXML
    private TextField publishingYearField;
    @FXML
    private TextField publisherField;

    //creating a new book
    public Books addBook(){
        String title = titleField.getText().trim();
        String lastName = authorsLastNameField.getText().trim();
        String firstName = authorsFirstNameField.getText().trim();
        String publishingYear = publishingYearField.getText().trim();
        String publisher = publisherField.getText().trim();

        Books newBook = new Books(title,lastName,firstName,publisher,publishingYear);
        return newBook;
    }


}
