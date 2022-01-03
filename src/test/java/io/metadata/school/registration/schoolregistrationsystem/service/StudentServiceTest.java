package io.metadata.school.registration.schoolregistrationsystem.service;

import io.metadata.school.registration.schoolregistrationsystem.SchoolRegistrationSystemApplication;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SchoolRegistrationSystemApplication.class)
@AutoConfigureMockMvc
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void whenCreatNewStudend()throws Exception {
        Student student = new Student("fName-1","lName");
        student = studentService.save(student);
        assertEquals(1L,student.getId());
    }


}
