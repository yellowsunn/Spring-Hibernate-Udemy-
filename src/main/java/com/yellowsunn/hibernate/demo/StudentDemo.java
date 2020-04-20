package com.yellowsunn.hibernate.demo;

import com.yellowsunn.hibernate.demo.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentDemo {
    public static void main(String[] args) {
        // create session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try (factory) {
            // start a transaction
            session.beginTransaction();

//            Student tempStudent1 = Student.builder().firstName("John").lastName("Doe").email("john@luv2code.com").build();
//            Student tempStudent2 = Student.builder().firstName("Mary").lastName("Public").email("mary@luv2code.com").build();
//            Student tempStudent3 = Student.builder().firstName("Bonita").lastName("Applebum").email("bonita@luv2code.com").build();
//            Student tempStudent4 = Student.builder().firstName("Daffy").lastName("Duck").email("daffy@luv2code.com").build();
//            session.save(tempStudent1);
//            session.save(tempStudent2);
//            session.save(tempStudent3);
//            session.save(tempStudent4);

            // query students
            List<Student> theStudents = session.createQuery("from Student").getResultList();

            // display the students
            displayStudents(theStudents);

            // query students: lastName= 'Doe'
            theStudents = session.createQuery("from Student s where s.lastName='Doe'").getResultList();

            // display the students
            System.out.println("\n\nStudents who have last name of Doe");
            displayStudents(theStudents);

            // commit the transaction
            session.getTransaction().commit();

            System.out.println("Done!");
        }
    }

    private static void displayStudents(List<Student> theStudents) {
        for (Student tempStudent : theStudents) {
            System.out.println(tempStudent);
        }
    }
}
