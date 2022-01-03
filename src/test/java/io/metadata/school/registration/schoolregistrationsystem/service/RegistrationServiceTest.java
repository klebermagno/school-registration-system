package io.metadata.school.registration.schoolregistrationsystem.service;


import io.metadata.school.registration.schoolregistrationsystem.SchoolRegistrationSystemApplication;
import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.RegistrationDublicatedException;
import io.metadata.school.registration.schoolregistrationsystem.exception.RegistrationMaxCoursesPerStudentException;
import io.metadata.school.registration.schoolregistrationsystem.exception.RegistrationMaxStudentPerCourseException;
import io.metadata.school.registration.schoolregistrationsystem.exception.RegistrationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static io.metadata.school.registration.schoolregistrationsystem.utils.RegistrationUtils.createASetOfRegistrationWithDiferentsCourses;
import static io.metadata.school.registration.schoolregistrationsystem.utils.RegistrationUtils.createASetOfRegistrationWithDiferentsStudents;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolRegistrationSystemApplication.class)
@AutoConfigureMockMvc
public class RegistrationServiceTest {



    @Mock
    private CourseService courseService;
    @Mock
    private StudentService studentService;
    @InjectMocks
    @Autowired
    private RegistrationService registrationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void whenRegistryStudend_thenMaxCoursePerStudent()  throws Exception {
        Student testStudent = new Student(1L,"FirstName", "LastName");
        Course course = new Course(6L,"CourseName");

        course.setRegistrations(createASetOfRegistrationWithDiferentsCourses(5,testStudent));
        testStudent.setRegistrations(course.getRegistrations());
        when(studentService.findById(1L)).thenReturn(testStudent);
        when(studentService.update(1,testStudent)).thenReturn(testStudent);
        when(courseService.findById(6L)).thenReturn(course);

        Throwable exception = assertThrows(RegistrationMaxCoursesPerStudentException.class,
                () -> registrationService.registry(1L,6L));
        assertEquals("Student exceeds course registration limit.", exception.getMessage());
    }

    @Test
    public void whenRegistryStudend_shouldValidateAndRegistryStudent()  throws Exception {
        Student testStudent = new Student(1L,"FirstName", "LastName");
        Course course = new Course(6L,"CourseName");

        course.setRegistrations(createASetOfRegistrationWithDiferentsCourses(4,testStudent));
        testStudent.setRegistrations(course.getRegistrations());
        when(studentService.findById(1L)).thenReturn(testStudent);
        when(studentService.update(1,testStudent)).thenReturn(testStudent);
        when(courseService.findById(6L)).thenReturn(course);
        Student registredStudent = registrationService.registry(1L,6L);
        assertEquals(5, registredStudent.getRegistrations().size());
    }

    @Test
    public void whenRegistryStudend_thenDuplicatedCourse()  throws Exception {
        Student testStudent = new Student(1L,"FirstName", "LastName");
        Course course = new Course(1L,"CourseName-1");

        course.setRegistrations(createASetOfRegistrationWithDiferentsCourses(4,testStudent));
        testStudent.setRegistrations(course.getRegistrations());
        when(studentService.findById(1L)).thenReturn(testStudent);
        when(studentService.update(1,testStudent)).thenReturn(testStudent);
        when(courseService.findById(1L)).thenReturn(course);

        Throwable exception = assertThrows(RegistrationDublicatedException.class,
                () -> registrationService.registry(1L,1L));
        assertEquals("The student has already been registered for the course.", exception.getMessage());
    }
    @Test
    public void whenUnRegistryStudend_thenNotPresent()  throws Exception {
        Student testStudent = new Student(1L,"FirstName", "LastName");
        Course course = new Course(1L,"CourseName-1");

        when(studentService.findById(1L)).thenReturn(testStudent);
        when(studentService.update(1,testStudent)).thenReturn(testStudent);
        when(courseService.findById(1L)).thenReturn(course);

        Throwable exception = assertThrows(RegistrationNotFoundException.class, () -> registrationService.unRegistry(1L,1L));
        assertEquals("The student is not registered for this course.", exception.getMessage());
    }
    @Test
    public void whenRegistryStudend_thenMaxStudentPerCurse()  throws Exception {
        Student testStudent = new Student(1L,"FirstName", "LastName");
        Course course = new Course(6L,"CourseName");

        course.setRegistrations(createASetOfRegistrationWithDiferentsStudents(50,course));

        when(studentService.findById(1L)).thenReturn(testStudent);
        when(studentService.update(1,testStudent)).thenReturn(testStudent);
        when(courseService.findById(6L)).thenReturn(course);

        Throwable exception = assertThrows(RegistrationMaxStudentPerCourseException.class, () -> registrationService.registry(1L,6L));
        assertEquals("The course exceeds student registration limit.", exception.getMessage());
    }



}
