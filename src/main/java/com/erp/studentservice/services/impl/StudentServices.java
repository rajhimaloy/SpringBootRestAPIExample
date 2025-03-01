package com.erp.studentservice.services.impl;

import com.erp.studentservice.model.Student;
import com.erp.studentservice.services.iservices.IStudentServices;
import org.springframework.stereotype.Service;

import java.util.Map;

/*
 * Author: Rajib Kumer Ghosh
 */

@Service
public class StudentServices implements IStudentServices {

    @Override
    public String getByName(String name) {
        Student student = new Student();
        student.setId(1001);
        student.setName(name);
        student.setAge(38);
        return "Student Details: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

    @Override
    public String createStudent(Student student) {
        return "Student Created: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

    @Override
    public String updateStudent(Student student) {
        return "Student Updated: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

    @Override
    public String updateStudentAge(Integer id, Integer age) {
        return "Student Partial Update with ID " + id + ", updated field: " + age;
    }

    @Override
    public String updateStudentPartial(Integer id, Map<String, Object> student) {
        return "Student with ID " + id + " updated fields: " + student;
    }

    @Override
    public String deleteStudent(Student student) {
        return "Student Deleted: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

}
