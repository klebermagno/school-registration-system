package io.metadata.school.registration.schoolregistrationsystem.utils;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.entity.Registration;
import io.metadata.school.registration.schoolregistrationsystem.entity.Student;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class RegistrationUtils {
    public static Set<Registration> createASetOfRegistrationWithDiferentsCourses(int n, Student student) {
        Set<Registration> registrations =new HashSet();

        IntStream.rangeClosed(1, n)
                .forEach(i->
                        registrations.add(
                                new Registration(new Long(i),student, new Course(new Long(i),"CourseName-%i"))));
        return  registrations;

    }

    public static Set<Registration>  createASetOfRegistrationWithDiferentsStudents(int n,Course course) {
        Set<Registration> registrations =new HashSet();

        IntStream.rangeClosed(1, n)
                .forEach(i->
                        registrations.add(
                                new Registration(new Long(i),new Student(new Long(i),"fName-%i","lName"),course)));
        return  registrations;

    }

}
