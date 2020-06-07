package com.library.visual.books;

import com.library.CURD.CRUDBooks;
import com.library.entity.Books;
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

public class BooksScreenController {

    private CRUDBooks crudBooks;

    @FXML
    private TableView<Books> booksTable;
    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorsLastNameField;
    @FXML
    private TextField authorsFirstNameField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField publishingYearField;
    @FXML
    private Button updateBookButton;
    @FXML
    private Button deleteBookButton;

    public void initialize() {

        crudBooks = new CRUDBooks();

        idField.setDisable(true);


        //populate table with book info
        booksTable.setItems(getBooks());

        //when clicked on a table row, populate text fields
        booksTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Books>() {
            @Override
            public void changed(ObservableValue<? extends Books> observableValue, Books books, Books newValue) {
                if (newValue != null) {
                    Books book = booksTable.getSelectionModel().getSelectedItem();
                    idField.setText(Integer.toString(book.getId()));
                    titleField.setText(book.getTitle());
                    authorsLastNameField.setText(book.getAuthorLastName());
                    authorsFirstNameField.setText(book.getAuthorFirstName());
                    publisherField.setText(book.getPublisher());
                    publishingYearField.setText(book.getPublishingYear());
                    // if a row is clicked enable update member button
                    if(book != null){
                        updateBookButton.setDisable(false);
                    }else{
                        updateBookButton.setDisable(true);
                    }
                    //if a book is'n on loan deleting is enabled
                    if(crudBooks.bookIsOnLoan(book)){
                        deleteBookButton.setDisable(false);
                    }else{
                        deleteBookButton.setDisable(true);
                    }
                }

            }
        });

        //only one row can be selected
        booksTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //initial state of update and delete buttons
        updateBookButton.setDisable(true);
        deleteBookButton.setDisable(true);

    }

    //get books from the DB
    private ObservableList<Books> getBooks() {
        List<Books> booksList = crudBooks.getAllBooks();

        ObservableList<Books> booksListObs = FXCollections.observableArrayList(booksList);

        return booksListObs;

    }

    //add a new book to the db
    @FXML
    private void addBook() {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(booksTable.getScene().getWindow());
        dialog.setTitle("Add new book");
        dialog.setHeaderText("Use this dialog to create new book");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addbookdialog.fxml"));

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
            AddBookController controller = fxmlLoader.getController();
            Books newBook = controller.addBook();
            crudBooks.addBook(newBook);
            booksTable.setItems(getBooks());
        }
        booksTable.getSelectionModel().selectFirst();
    }

    //delete a book from the db
    public void deleteBook(){
        Books book = booksTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Delete a Book");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete selected Book: "+ book.getTitle());

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get()==ButtonType.OK){
            crudBooks.deleteBook(book);
            booksTable.setItems(getBooks());
            idField.setText("");
            titleField.setText("");
            authorsFirstNameField.setText("");
            authorsLastNameField.setText("");
            publisherField.setText("");
            publishingYearField.setText("");
        }
        booksTable.getSelectionModel().selectFirst();
    }

    //update a book in the db
    public void updateBook(){
        Books book = booksTable.getSelectionModel().getSelectedItem();
        int id = book.getId();
        String title= titleField.getText();
        String lastName= authorsLastNameField.getText();
        String firstName= authorsFirstNameField.getText();
        String publisher= publisherField.getText();
        String publishingYear= publishingYearField.getText();

        crudBooks.updateBook(id, title, lastName, firstName, publisher, publishingYear);
        booksTable.setItems(getBooks());
        booksTable.getSelectionModel().selectFirst();
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
