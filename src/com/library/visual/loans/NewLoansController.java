package com.library.visual.loans;

import com.library.CURD.BooksOnLoan;
import com.library.CURD.CRUDMembers;
import com.library.entity.Books;
import com.library.entity.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NewLoansController {
    private BooksOnLoan booksOnLoan;
    private CRUDMembers crudMembers;
    @FXML
    ListView<Members> membersListView;
    @FXML
    TableView<Books> booksTableView;
    @FXML
    Button newLoanButton;

    public void initialize() {
        booksOnLoan = new BooksOnLoan();
        crudMembers = new CRUDMembers();

        membersListView.setItems(getMembers());
        booksTableView.setItems(getBooks());
        
        membersListView.getSelectionModel().selectFirst();
        booksTableView.getSelectionModel().selectFirst();
    }

    //returns a list of members
    private ObservableList<Members> getMembers() {
        List<Members> members = crudMembers.getAllMembers();
        ObservableList<Members> membersListObs = FXCollections.observableArrayList(members);
        return membersListObs;
    }

    //returns a list of books that are not loaned
    private ObservableList<Books> getBooks(){
        List<Books> books = booksOnLoan.getBooksNotOnLoan();
        ObservableList<Books> booksListObs = FXCollections.observableArrayList(books);
        return booksListObs;
    }

    //loan a book
    @FXML
    public void newLoan() {
        Members member = membersListView.getSelectionModel().getSelectedItem();
        Books book = booksTableView.getSelectionModel().getSelectedItem();
        booksOnLoan.loan(member, book);
        booksTableView.setItems(getBooks());
        booksTableView.getSelectionModel().selectFirst();

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
