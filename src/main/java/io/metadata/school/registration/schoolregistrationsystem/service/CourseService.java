package io.metadata.school.registration.schoolregistrationsystem.service;

import io.metadata.school.registration.schoolregistrationsystem.entity.Course;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseException;
import io.metadata.school.registration.schoolregistrationsystem.exception.CourseNotFoundException;
import io.metadata.school.registration.schoolregistrationsystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;


    public Course findById(long id) throws CourseNotFoundException{
        Course course=null;
        course = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course not found, id : " + id));

        return course;
    }

    public List<Course> findAll() throws CourseException{
        List<Course> courses;
        try {
            courses = courseRepository.findAll();
        } catch (Exception e) {
            throw new CourseException(e.getMessage());
        }

        return courses;

    }

    public Course save(Course course) throws CourseException{

        try {
            Course newCourse = courseRepository.save(new Course(course.getName()));
            return newCourse;
        } catch (Exception e) {
            throw new CourseException(e.getMessage());
        }
    }

    public Course update(long id,Course course) throws  CourseNotFoundException, CourseException{

        Course updetedCourse = this.findById(id);

        updetedCourse.setName(course.getName());
        try{
            return  courseRepository.save(updetedCourse);
        }catch (Exception e) {
            throw new CourseException(e.getMessage());
        }
    }


    public void deleteById(long id) throws  CourseNotFoundException {
        this.findById(id);
        courseRepository.deleteById(id);

    }
}

