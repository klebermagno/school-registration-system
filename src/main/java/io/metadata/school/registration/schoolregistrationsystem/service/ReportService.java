package io.metadata.school.registration.schoolregistrationsystem.service;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.entity.Registration;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseException;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentException;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RegistrationRepository registrationRepository;

    public List<Student> getCourseStutend(long courseId) throws CourseNotFoundException, CourseException {
        Course course = courseService.findById(courseId);

        List<Student> students = course.getRegistrations().stream().map(Registration::getStudent).collect(Collectors.toList());
        return students;
    }

    public List<Course> getStudentCourses(long studentId) throws StudentNotFoundException, StudentException {

        Student student = studentService.findById(studentId);

        List<Course> courses = student.getRegistrations().stream().map(Registration::getCourse).collect(Collectors.toList());
        return courses;


    }

    public List getCoursesWithoutAnyStudents() {
        return registrationRepository.getCoursesWithoutAnyStudents();
    }

    public List getStudentsWithoutAnyCourses() {
       return registrationRepository.getStudentsWithoutAnyCourses();
    }
}
