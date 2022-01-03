package io.metadata.school.registration.schoolregistrationsystem.controller;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseException;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentException;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ReportController {

    @Autowired
    private ReportService reportService;


    @GetMapping("/reports/course/{id}/students")
    public ResponseEntity<List<Student>> getCourseStudents(@PathVariable("id") long courseId) {

        List students = null;
        try {
            students = reportService.getCourseStutend(courseId);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CourseException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/reports/student/{id}/courses")
    public ResponseEntity<List<Course>> getStudentCourses(@PathVariable("id") long studentId) {
        List courses = null;
        try {
            courses= reportService.getStudentCourses(studentId);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }

    @GetMapping("/reports/allStudentsWithoutAnyCourses")
    public ResponseEntity<List<Student>> getStudentsWithoutAnyCourses() {

        return new ResponseEntity<>( reportService.getStudentsWithoutAnyCourses(), HttpStatus.OK);
    }

    @GetMapping("/reports/allCoursesWithoutAnyStudents")
    public ResponseEntity<List<Course>> getCoursesWithoutAnyCourses() {

        return new ResponseEntity<>(reportService.getCoursesWithoutAnyStudents(),HttpStatus.OK);

    }

}
