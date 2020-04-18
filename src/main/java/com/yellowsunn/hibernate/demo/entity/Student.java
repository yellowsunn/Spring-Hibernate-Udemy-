package com.yellowsunn.hibernate.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter

@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "first_name")
    @NonNull private String firstName;

    @Column(name = "last_name")
    @NonNull private String lastName;

    @Column(name = "email")
    @NonNull private String email;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
