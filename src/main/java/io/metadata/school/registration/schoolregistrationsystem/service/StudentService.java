package io.metadata.school.registration.schoolregistrationsystem.service;

import io.metadata.school.registration.schoolregistrationsystem.entity.Student;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentException;
import io.metadata.school.registration.schoolregistrationsystem.exception.StudentNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getStudent() throws StudentException {
        try {
            return studentRepository.findAll();
        }catch (Exception e){
            throw new StudentException(e.getMessage());
        }
    }

    public Student findById(long id) throws StudentException, StudentNotFoundException {
        Student student = null;

        student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found, id : " + id));

        return student;
    }

    public Student save(Student student) throws StudentException {
        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        try {
            return studentRepository.save(newStudent);
        } catch (Exception e) {
            throw new StudentException(e.getMessage());
        }
    }

    public Student update(long id, Student student) throws StudentException, StudentNotFoundException {
        Student updatedStudent = this.findById(id);

        updatedStudent.setFirstName(student.getFirstName());
        updatedStudent.setLastName(student.getLastName());
        try {
            return studentRepository.save(updatedStudent);
        } catch (Exception e) {
            throw new StudentException(e.getMessage());
        }

    }

    public void deleteById(long id) throws StudentException, StudentNotFoundException {
        this.findById(id);
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            throw new StudentException(e.getMessage());
        }
    }
}
