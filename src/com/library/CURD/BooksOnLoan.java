package com.library.CURD;

import com.library.entity.Books;
import com.library.entity.Factory;
import com.library.entity.Members;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BooksOnLoan {
    SessionFactory factory;
    Session session;

    public BooksOnLoan() {
        this.factory = Factory.getSessionFactory();
    }

    //get members that have loans
    public List<Members> getMembers() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Members> members = session.createQuery("from Members as m inner join fetch m.books").getResultList();
        session.getTransaction().commit();
        session.close();

        List<Members> members1 = members.stream().distinct().collect(Collectors.toList());

        return members1;
    }

    //get books that are on loan
    public List<Books> getBooksOnLoan(Members member) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Books> books = session.createQuery("from Books where id_member = :id").setParameter("id", member.getId()).getResultList();
        session.getTransaction().commit();
        session.close();
        return books;
    }

    //get books that are not loaned
    public List<Books> getBooksNotOnLoan(){
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Books> books = session.createQuery("from Books where id_member = NULL").getResultList();
        session.getTransaction().commit();
        session.close();
        return books;
    }

    //return a book
    public void returnBook(Books book) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        Books bookReturn = session.get(Books.class, book.getId());
        bookReturn.setMembers(null);
        session.save(bookReturn);
        session.getTransaction().commit();
        session.close();
    }

    //loan a book
    public void loan(Members member, Books book) {
        session = factory.getCurrentSession();
        session.beginTransaction();

        Members member1 = (Members) session.createQuery("from Members as m left join fetch m.books where m.id = :id").setParameter("id", member.getId()).getSingleResult();
        Books book1 = (Books) session.createQuery("from Books where id = :id").setParameter("id",book.getId()).getSingleResult();
        member1.addBooks(book1);

        session.getTransaction().commit();
        session.close();
    }
}
