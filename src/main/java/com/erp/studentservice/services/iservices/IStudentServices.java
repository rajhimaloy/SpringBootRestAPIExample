package com.erp.studentservice.services.iservices;

import com.erp.studentservice.model.Student;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Author: Rajib Kumer Ghosh
 */

public interface IStudentServices {
    public String getByName(String name);
    public String createStudent(Student student);
    public String updateStudent(Student student);
    public String updateStudentAge(Integer id, Integer age);
    public String updateStudentPartial(Integer id, Map<String, Object> student);
    public String deleteStudent(Student student);

}
