package com.erp.studentservice.model;

/*
 * Author: Rajib Kumer Ghosh
 */

public class Student {
    private Integer id;

    private String name;

    private Integer age;

    // Constructors
    public Student() {}

    public Student(Integer id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

