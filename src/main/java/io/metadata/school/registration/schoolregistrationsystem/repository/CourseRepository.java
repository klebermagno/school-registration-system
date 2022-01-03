package io.metadata.school.registration.schoolregistrationsystem.repository;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course, Long> {

}