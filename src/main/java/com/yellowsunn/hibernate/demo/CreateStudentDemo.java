package com.yellowsunn.hibernate.demo;

import com.yellowsunn.hibernate.demo.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class CreateStudentDemo {
    public static void main(String[] args) {
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        // create session

        try (factory) {
            Session session = factory.getCurrentSession();
            // create a student object
            System.out.println("Creating new student object...");
            Student tempStudent = new Student("Paul", "Wall", "paul@luv2code.com");
            System.out.println(tempStudent.toString());
            // start a transaction
            session.beginTransaction();

            // save the student object
            System.out.println("Saving the student");
            session.save(tempStudent);

            // commit transaction
            session.getTransaction().commit();
            System.out.println("Done!");
        }
    }
}
