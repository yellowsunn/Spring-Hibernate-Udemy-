package com.yellowsunn.hibernate.demo;

import com.yellowsunn.hibernate.demo.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class StudentDemo {
    public static void main(String[] args) {
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (factory) {
            // create session
            Session session = factory.getCurrentSession();

            // create 3 student objects
            System.out.println("Creating new student object...");
            Student tempStudent1 = Student.builder().firstName("John").lastName("Doe").email("john@luv2code.com").build();
            Student tempStudent2 = Student.builder().firstName("Mary").lastName("Public").email("mary@luv2code.com").build();
            Student tempStudent3 = Student.builder().firstName("Bonita").lastName("Applebum").email("bonita@luv2code.com").build();

            // start a transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the students...");
            session.save(tempStudent1);
            session.save(tempStudent2);
            session.save(tempStudent3);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("Done!");
        }
    }
}
