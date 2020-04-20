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

            // create a student object
            System.out.println("Creating new student object...");
            Student tempStudent = Student.builder().firstName("Daffy").lastName("Duck").email("daffy@luv2code.com").build();

            // start a transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the student...");
            System.out.println(tempStudent);
            session.save(tempStudent);

            // commit transaction
            session.getTransaction().commit();

            // MY NEW CODE
            // find out the student's id : primary key
            System.out.println("Saved student. Generated id : " + tempStudent.getId());

            // now get a new session and start transaction
            session = factory.getCurrentSession();
            session.beginTransaction();

            // retrieve student based on the id : primary key
            System.out.println("\nGetting student with id : " + tempStudent.getId());

            Student myStudent = session.get(Student.class, tempStudent.getId());
            System.out.println("Get complete : " + myStudent);

            // commit the transaction
            session.getTransaction().commit();

            System.out.println("Done!");
        }
    }
}
