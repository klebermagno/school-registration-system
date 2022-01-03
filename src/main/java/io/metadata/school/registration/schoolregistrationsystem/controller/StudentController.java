package io.metadata.school.registration.schoolregistrationsystem.controller;

import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentException;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAll() {
        try {
            List<Student> students = studentService.getStudent();

            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getById(@PathVariable("id") long id) {
        try {
            Student retStudent = studentService.findById(id);
            return new ResponseEntity<Student>(retStudent, HttpStatus.OK);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @PostMapping("/students")
    public ResponseEntity<Student> create(@RequestBody Student student) {
        try {
            Student studentRet = studentService.save(student);
            return new ResponseEntity<>(studentRet, HttpStatus.CREATED);
        } catch (StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> update(@PathVariable("id") long id, @RequestBody Student student) {
        try {
            Student retStudent = studentService.update(id, student);
            return new ResponseEntity<Student>(retStudent, HttpStatus.OK);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        try {
            studentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}