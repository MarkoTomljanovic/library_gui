package com.library.CURD;

import com.library.entity.Books;
import com.library.entity.Factory;
import com.library.entity.Members;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class CRUDMembers {
    SessionFactory factory;
    Session session;


    public CRUDMembers() {
        this.factory = Factory.getSessionFactory();
    }

    //get a list of all members from the DB
    public List<Members> getAllMembers() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Members> membersList = session.createQuery("from Members").getResultList();
        session.close();
        return membersList;
    }

    //add a member to the db
    public void addMember(Members member) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(member);
        session.getTransaction().commit();
        session.close();
    }

    //delete a member from the db
    public void deleteMember(Members member) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        int id = member.getId();
        Members memberDel = session.get(Members.class, id);
        session.delete(memberDel);
        session.getTransaction().commit();
        session.close();
    }

    //update member info
    public void updateMember(int id, String firstName, String lastName, String address, String phoneNum, String email) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        Members memberUpdate = session.get(Members.class, id);
        memberUpdate.setFirstName(firstName);
        memberUpdate.setLastName(lastName);
        memberUpdate.setAddress(address);
        memberUpdate.setPhoneNum(phoneNum);
        memberUpdate.setEmail(email);
        session.getTransaction().commit();
        session.close();
    }

    //return true if member has books on loan
    public boolean memberHasBooks(Members member) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<Members> members = session.createQuery("from Members as m left join fetch m.books where m.id = :id").setParameter("id", member.getId()).getResultList();
        session.close();
        List<Books> books = members.get(0).getBooks();
        if(books.isEmpty()){
            return true;
        }
        return false;
    }
}
