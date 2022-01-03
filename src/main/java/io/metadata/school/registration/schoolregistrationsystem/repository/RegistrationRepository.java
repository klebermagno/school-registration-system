package io.metadata.school.registration.schoolregistrationsystem.repository;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.entity.Registration;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Integer> {

    @Query("SELECT student FROM Student student left JOIN Registration registration on " +
            "student.id = registration.student.id where registration.course.id is null")
    List<Student> getStudentsWithoutAnyCourses();

    @Query("SELECT course FROM Course course left JOIN Registration registration on" +
            " course.id = registration.course.id where registration.student.id is null")
    List<Course> getCoursesWithoutAnyStudents();
}