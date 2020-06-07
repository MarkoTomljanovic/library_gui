package com.library.visual.loans;

import com.library.CURD.BooksOnLoan;
import com.library.entity.Books;
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

public class LoansController {
    private BooksOnLoan booksOnLoan;

    @FXML
    private TableView<Books> booksTableView;
    @FXML
    private ListView<Members> membersListView;
    @FXML
    private Button returnBookButton;



    public void initialize() {

        booksOnLoan = new BooksOnLoan();

        //populate ListView with members that have books on loan
        membersListView.setItems(getMembers());

        //when clicked on ListView populate TableView with books on loan
        membersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Members>() {
            @Override
            public void changed(ObservableValue<? extends Members> observableValue, Members members, Members t1) {
                if (t1 != null) {
                    Members member = membersListView.getSelectionModel().getSelectedItem();
                    booksTableView.setItems(getBooks(member));
                    booksTableView.getSelectionModel().selectFirst();
                }

            }
        });
        //when clicked TableView item enable return button
        booksTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Books>() {
            @Override
            public void changed(ObservableValue<? extends Books> observableValue, Books books, Books t1) {
                if (t1 != null) {
                    Books book = booksTableView.getSelectionModel().getSelectedItem();

                    if(book != null) {
                        returnBookButton.setDisable(false);
                    }else{
                        returnBookButton.setDisable(true);
                    }
                }
            }
        });

        returnBookButton.setDisable(true);
    }

    //returns a list of books that are on loan
    private ObservableList<Books> getBooks(Members member){
        List<Books> books = booksOnLoan.getBooksOnLoan(member);
        ObservableList<Books> booksListObs = FXCollections.observableArrayList(books);
        return booksListObs;
    }

    //returns a list of members that have books on loan
    private ObservableList<Members> getMembers(){
        List<Members> members = booksOnLoan.getMembers();
        ObservableList<Members> membersListObs = FXCollections.observableArrayList(members);
        return membersListObs;
    }


    //return a book
    @FXML
    public void returnBook() {
        Books book = booksTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Return a Book");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to return selected Book: " + book.getTitle());

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            booksOnLoan.returnBook(book);
            booksTableView.getItems().clear();
            membersListView.setItems(getMembers());
            membersListView.getSelectionModel().selectFirst();
        }
        if(membersListView.getItems().isEmpty()){
            returnBookButton.setDisable(true);
        }
    }

    //goto add new loans screen
    @FXML
    public void newLoan(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("newloans.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Loans Management");
            stage.setScene(new Scene(root, 1500, 900));
            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
