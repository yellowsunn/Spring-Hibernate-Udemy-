package com.yellowsunn.mvc.student;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter @Setter
public class Student {
    private String firstName;
    private String lastName;

    private String country;
    private String favoriteLanguage;

    @Setter(AccessLevel.NONE)
    private LinkedHashMap<String, String> favoriteLanguageOptions;

    private String[] operatingSystems;

    public Student() {
        favoriteLanguageOptions = new LinkedHashMap<>();
        favoriteLanguageOptions.put("Java", "자바");
        favoriteLanguageOptions.put("C#", "C#");
        favoriteLanguageOptions.put("PHP", "PHP");
        favoriteLanguageOptions.put("Ruby", "루비");
    }
}
