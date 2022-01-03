package io.metadata.school.registration.schoolregistrationsystem.service;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.entity.Registration;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.*;
import io.metadata.school.registration.schoolregistrationsystem.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private static final  int MAX_COUSER_PER_STUDENT = 5;
    private static final int MAX_STUDENT_PER_COURSE = 50;


    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private RegistrationRepository registrationRepository;

    public Student registry(long studentId, long courseId) throws StudentNotFoundException, StudentException, CourseNotFoundException, CourseException, RegistrationMaxCoursesPerStudentException, RegistrationMaxStudentPerCourseException, RegistrationDublicatedException {
        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);

        validadeDuplicatedRegistration(student,courseId);
        validateCourseLimit(course);
        validateStudentsLimit(student);

        Registration registration = new Registration();
        registration.setStudent(student);
        registration.setCourse(course);
        student.getRegistrations().add(registration);
        studentService.update(studentId,student);
        return student;
    }



    public Student unRegistry(long studentId, long courseId) throws StudentNotFoundException, StudentException, CourseNotFoundException, CourseException, RegistrationMaxCoursesPerStudentException, RegistrationMaxStudentPerCourseException, RegistrationNotFoundException {
        Student student = studentService.findById(studentId);
        Course course = courseService.findById(courseId);

        Optional<Registration> registrationData =  student.getRegistrations().stream().filter(registration -> registration.getCourse().getId().equals(courseId)).findFirst();

        validateStudentIsRegistred(student,courseId);
        registrationRepository.delete(registrationData.get());
        course.getRegistrations().remove(registrationData.get());
        student.getRegistrations().remove(registrationData.get());
        studentService.update(studentId,student);
        return student;
    }

    private void validateStudentIsRegistred(Student student, long courseId) throws RegistrationNotFoundException {
        Optional<Registration> registrationData =  student.getRegistrations().stream().filter(registration -> registration.getCourse().getId().equals(courseId)).findFirst();
        if(!registrationData.isPresent()){
            throw new RegistrationNotFoundException("The student is not registered for this course.");
        }
    }

    private void validadeDuplicatedRegistration(Student student, long courseId) throws CourseException, RegistrationDublicatedException {
        Optional<Registration> registrationData =  student.getRegistrations().stream().filter(registration -> registration.getCourse().getId().equals(courseId)).findFirst();
        if(registrationData.isPresent()){
            throw new RegistrationDublicatedException("The student has already been registered for the course.");
        }
    }

    private void validateStudentsLimit(Student student) throws RegistrationMaxCoursesPerStudentException {
        if (student.getRegistrations().size()>=this.MAX_COUSER_PER_STUDENT){
            throw new RegistrationMaxCoursesPerStudentException("Student exceeds course registration limit.");
        }
    }

    private void validateCourseLimit(Course course) throws RegistrationMaxCoursesPerStudentException, RegistrationMaxStudentPerCourseException {
        if (course.getRegistrations().size()>=this.MAX_STUDENT_PER_COURSE){
            throw new RegistrationMaxStudentPerCourseException("The course exceeds student registration limit.");
        }
    }


}
