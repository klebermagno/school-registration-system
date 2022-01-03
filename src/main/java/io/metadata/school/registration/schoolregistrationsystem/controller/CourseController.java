package io.metadata.school.registration.schoolregistrationsystem.controller;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseException;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
@CrossOrigin(origins = "*")
public class CourseController {


    @Autowired
    private CourseService courseService;

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getById(@PathVariable("id") long id) {

        try {
            Course course = courseService.findById(id);
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAll() {
        try {
            List<Course> courses = courseService.findAll();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (CourseException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> create(@RequestBody Course course) {
        try {

            course = courseService.save(course);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (CourseException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> update(@PathVariable("id") long id, @RequestBody Course course) {
        Course updatedCourse = null;
        try {
            updatedCourse = courseService.update(id, course);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CourseException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {

        try {
            courseService.deleteById(id);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
