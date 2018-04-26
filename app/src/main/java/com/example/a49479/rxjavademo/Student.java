package com.example.a49479.rxjavademo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 49479 on 2018/4/23.
 */

public class Student {
    private String name;
    private List<Course> courses ;

    public Student(Course[] arrCourses, String name) {
        courses = new ArrayList<>();
        for(Course c:arrCourses){
            courses.add(c);
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
