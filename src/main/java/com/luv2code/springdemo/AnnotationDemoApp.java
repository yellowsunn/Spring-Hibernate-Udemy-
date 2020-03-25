package com.luv2code.springdemo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotationDemoApp {
    public static void main(String[] args) {
        // read spring config file
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // get the bean from spring container
        Coach theCoach = context.getBean("tennisCoach", Coach.class);
        Coach alphaCoach = context.getBean("tennisCoach", Coach.class);

        // check if they are the same
        boolean result = (theCoach == alphaCoach);

        // print out the result
        System.out.println("\nPointing to the same object: " + result);
        System.out.println("\nMemory location for theCoach" + theCoach);
        System.out.println("\nMemory location for theCoach" + alphaCoach + "\n");

        // close the context
        context.close();
    }
}
