package io.metadata.school.registration.schoolregistrationsystem.exception;

import java.util.function.Supplier;

public class CourseNotFoundException  extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }

}
