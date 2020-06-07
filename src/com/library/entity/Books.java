package com.library.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "books")
//@Access(AccessType.PROPERTY)
public class Books {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private StringProperty authorLastName = new SimpleStringProperty();
    private StringProperty authorFirstName = new SimpleStringProperty();
    private StringProperty publisher = new SimpleStringProperty();
    private StringProperty publishingYear = new SimpleStringProperty();
    private Members members;

    public Books() {
    }

    public Books(String title, String authorLastName, String authorFirstName, String publisher, String publishingYear) {
        setTitle(title);
        setAuthorLastName(authorLastName);
        setAuthorFirstName(authorFirstName);
        setPublisher(publisher);
        setPublishingYear(publishingYear);
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

    @Column(name = "title")
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @Column(name = "author_last_name")
    public String getAuthorLastName() {
        return authorLastName.get();
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName.set(authorLastName);
    }

    @Column(name = "author_first_name")
    public String getAuthorFirstName() {
        return authorFirstName.get();
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName.set(authorFirstName);
    }

    @Column(name = "publisher")
    public String getPublisher() {
        return publisher.get();
    }

    public void setPublisher(String publisher) {
        this.publisher.set(publisher);
    }

    @Column(name = "publishing_year")
    public String getPublishingYear() {
        return publishingYear.get();
    }

    public void setPublishingYear(String publishingYear) {
        this.publishingYear.set(publishingYear);
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "id_member")
    public Members getMembers() {
        return members;
    }

    public void setMembers(Members members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Books))
            return false;
        Books books = (Books) o;
        return id.equals(books.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
