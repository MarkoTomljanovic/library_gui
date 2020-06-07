package com.library.entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Factory {

    public static SessionFactory factory;


    //private to disallow creating instances by other classes.
    private Factory() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (factory == null) {
            Factory.factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Members.class)
                    .addAnnotatedClass(Books.class)
                    .buildSessionFactory();
        }
        return factory;
    }

    public static synchronized void closeFactory(){
        factory.close();
    }
}
