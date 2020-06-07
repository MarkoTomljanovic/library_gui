package com.library.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "members")
public class Members {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty firstName = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty phoneNum = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private List<Books> books;

    public Members() {
    }

    public Members(String firstName, String lastName, String address, String phoneNum, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhoneNum(phoneNum);
        setEmail(email);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Column(name = "address")
    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    @Column(name = "phone_num")
    public String getPhoneNum() {
        return phoneNum.get();
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum.set(phoneNum);
    }

    @Column(name = "email")
    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @OneToMany(mappedBy = "members", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public void addBooks(Books book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setMembers(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Members))
            return false;
        Members member = (Members) o;
        return id == member.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getLastName() + ", " + getFirstName() + " ";
    }
}
