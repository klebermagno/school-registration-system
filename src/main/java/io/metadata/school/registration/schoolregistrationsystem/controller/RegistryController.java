package io.metadata.school.registration.schoolregistrationsystem.controller;

import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.*;
import io.metadata.school.registration.schoolregistrationsystem.exception.RegistrationMaxStudentPerCourseException;
import io.metadata.school.registration.schoolregistrationsystem.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class RegistryController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/students/{studentId}/register/{courseId}", method = RequestMethod.POST)
    public ResponseEntity<Student> register(@PathVariable("studentId") long studentId, @PathVariable("courseId") long courseId) {
        Student student = null;
        try {
            student=  registrationService.registry(studentId,courseId);
        } catch (StudentNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (StudentException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (CourseNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (CourseException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RegistrationMaxCoursesPerStudentException e) {
            return new ResponseEntity("Student exceeds course registration limit.", HttpStatus.BAD_REQUEST);
        } catch (RegistrationMaxStudentPerCourseException e) {
            return new ResponseEntity("The course exceeds student registration limit.", HttpStatus.BAD_REQUEST);
        } catch (RegistrationDublicatedException e) {
            return new ResponseEntity("The student has already been registered for the course.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);

    }

    @RequestMapping(value = "/students/{studentId}/unregister/{courseId}", method = RequestMethod.POST)
    public ResponseEntity<Student> unRegister(@PathVariable("studentId") long studentId, @PathVariable("courseId") long courseId) {
        Student student = null;
        try {
            student = registrationService.unRegistry(studentId,courseId);
        } catch (StudentNotFoundException e) {
            e.printStackTrace();
        } catch (StudentException e) {
            e.printStackTrace();
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
        } catch (CourseException e) {
            e.printStackTrace();
        } catch (RegistrationMaxCoursesPerStudentException e) {
            return new ResponseEntity("Student exceeds course registration limit.", HttpStatus.BAD_REQUEST);
        } catch (RegistrationMaxStudentPerCourseException e) {
            return new ResponseEntity("The course exceeds student registration limit.", HttpStatus.BAD_REQUEST);
        }catch (RegistrationNotFoundException e){
            return new ResponseEntity("Registration not found.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
