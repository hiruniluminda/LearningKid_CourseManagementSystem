package com.example.CourseManagement;

import com.example.CourseManagement.Course;
import com.example.CourseManagement.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course addCourse(CourseDto course) {
        return courseRepository.save(new Course(course.getId(), course.getName(), course.getDescription(), course.getInstructor()));
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    public Course updateCourse(Long id, CourseDto courseDto) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();

            existingCourse.setName(courseDto.getName());
            existingCourse.setDescription(courseDto.getDescription());
            existingCourse.setInstructor(courseDto.getInstructor());

            return courseRepository.save(existingCourse);
        } else {
            return null;
        }
    }

}
