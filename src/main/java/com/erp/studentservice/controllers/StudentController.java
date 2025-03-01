package com.erp.studentservice.controllers;

import com.erp.studentservice.model.Student;
import com.erp.studentservice.services.iservices.IStudentServices;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
* Author: Rajib Kumer Ghosh
*/

@RestController
@RequestMapping("/student")
public class StudentController {

    private IStudentServices iStudentServices;

    public StudentController(IStudentServices iStudentServices) {
        this.iStudentServices = iStudentServices;
    }

    /*GET http://localhost:8080/api/rest/student/getbyid?id=1001&age=38*/
    @GetMapping("/getbyid")
    public String getByID(@RequestParam("id") Integer id, @RequestParam("age") Integer age) {
        return "Student: " + id + ", Name: Rajib Kumer Ghosh, Age: " + age;
    }

    /*GET http://localhost:8080/api/rest/student/getbyname/Rajib Kumer Ghosh*/
    @GetMapping("/getbyname/{name}")
    public String getByName(@PathVariable("name") String name) {
        return iStudentServices.getByName(name);
    }

    /*POST http://localhost:8080/api/rest/student/createstudent
    {
        "id":"1",
        "name": "Rajib Kumer Ghosh",
        "age": 35
    }*/
    @PostMapping("/createstudent")
    public String createStudent(@RequestBody Student student) {
        return iStudentServices.createStudent(student);
    }

    /*PUT http://localhost:8080/api/rest/student/updatestudent
    {
        "id":"1",
        "name": "Rajib Kumer Ghosh",
        "age": 38
    }*/
    @PutMapping("/updatestudent")
    public String updateStudent(@RequestBody Student student) {
        return iStudentServices.updateStudent(student);
    }

    /*PATCH http://localhost:8080/api/rest/student/updatestudentage/1001
    {
        "age": 39
    }*/
    @PatchMapping("/updatestudentage/{id}")
    public String updateStudentAge(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Integer age = (Integer) updates.get("age");
        return iStudentServices.updateStudentAge(id, age);
    }

    /*PATCH http://localhost:8080/api/rest/student/updatestudentpartial/1001
    {
        "name": "Rajib Kumer Ghosh",
        "age": 39
    }*/
    @PatchMapping("/updatestudentpartial/{id}")
    public String updateStudentPartial(@PathVariable Integer id, @RequestBody Map<String, Object> student) {
        return iStudentServices.updateStudentPartial(id, student);
    }

    /*DELETE http://localhost:8080/api/rest/student/deletestudent
    {
        "id":"1",
        "name": "Rajib Kumer Ghosh",
        "age": 39
    }*/
    @DeleteMapping("/deletestudent")
    public String deleteStudent(@RequestBody Student student) {
        return iStudentServices.deleteStudent(student);
    }


}
