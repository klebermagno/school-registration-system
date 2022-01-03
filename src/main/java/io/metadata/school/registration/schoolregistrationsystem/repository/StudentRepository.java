package io.metadata.school.registration.schoolregistrationsystem.repository;


import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findOneByFirstNameOrLastName(String firstName, String lastName);

}

