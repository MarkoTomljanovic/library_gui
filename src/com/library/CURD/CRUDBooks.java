package com.library.CURD;

import com.library.entity.Books;
import com.library.entity.Factory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CRUDBooks {
    SessionFactory factory;
    Session session;

    public CRUDBooks() {
        this.factory = Factory.getSessionFactory();
    }

    //get all books from the db
    public List<Books> getAllBooks(){
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Books> booksList = session.createQuery("from Books").getResultList();
        session.close();
        return booksList;
    }

    //add a book to the db
    public void addBook(Books book){
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(book);
        session.getTransaction().commit();
        session.close();
    }

    //delete a book from the db
    public void deleteBook(Books book){
        session = factory.getCurrentSession();
        session.beginTransaction();
        int id = book.getId();
        Books bookDel = session.get(Books.class,id);
        session.delete(bookDel);
        session.getTransaction().commit();
        session.close();
    }

    //update a book in the db
    public void updateBook(int id, String title, String authorLastName, String authorFirstName, String publisher, String publishingYear){
        session = factory.getCurrentSession();
        session.beginTransaction();
        Books bookUpdate = session.get(Books.class,id);
        bookUpdate.setTitle(title);
        bookUpdate.setAuthorLastName(authorLastName);
        bookUpdate.setAuthorFirstName(authorFirstName);
        bookUpdate.setPublisher(publisher);
        bookUpdate.setPublishingYear(publishingYear);
        session.getTransaction().commit();
        session.close();
    }

    //return true if book is on loan
    public boolean bookIsOnLoan(Books book) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        Books bookHelp = (Books) session.createQuery("from Books where id = :id").setParameter("id",book.getId()).getSingleResult();
        session.close();
        if(bookHelp.getMembers() == null){
            return true;
        }
        return false;
    }

}
