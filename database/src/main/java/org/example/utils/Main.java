package org.example.utils;

import org.hibernate.SessionFactory;

public class Main {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    public static void main(String[] args) {
        TestDataImporter.importData(sessionFactory);
        sessionFactory.close();
    }
}
