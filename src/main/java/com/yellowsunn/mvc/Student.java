package com.yellowsunn.mvc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter @Setter
public class Student {
    private String firstName;
    private String lastName;

    private String country;

    private String favoriteLanguage;
    // 좋아하는 언어 옵션들
    @Setter(AccessLevel.NONE)
    private LinkedHashMap<String, String> favoriteLanguageOptions;

    // 운영체제
    private String[] operatingSystems;

    public Student() {
        favoriteLanguageOptions = new LinkedHashMap<>();
//        favoriteLanguageOptions.put("Java", "Java");
//        favoriteLanguageOptions.put("C#", "C#");
//        favoriteLanguageOptions.put("PHP", "PHP");
//        favoriteLanguageOptions.put("Ruby", "Ruby");
        favoriteLanguageOptions.put("Java", "자바");
        favoriteLanguageOptions.put("C#", "C#");
        favoriteLanguageOptions.put("PHP", "PHP");
        favoriteLanguageOptions.put("Ruby", "루비");
    }
}