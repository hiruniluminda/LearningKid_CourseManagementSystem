package com.example.CourseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private LessonService lessonService;

    // Fetch all courses
    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        List<CourseDto> courseDtos = courseService.getAllCourses();
        return new ResponseEntity<>(courseDtos, HttpStatus.OK);
    }



    // Add a new course
    @PostMapping("/create")
    public ResponseEntity<Course> addCourse(@RequestBody CourseDto courseDto) {
        try {
            Course course = courseService.addCourse(courseDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Add a lesson to a course
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Lesson> addLesson(@PathVariable int courseId, @RequestBody Lesson lesson) {
        try {
            // Check if the course exists
            Course course = courseService.getCourseById(courseId);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Assign the course to the lesson
            lesson.setCourse(course);

            // Save the lesson
            Lesson savedLesson = lessonService.saveLesson(lesson);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedLesson);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Delete a course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Update a course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody CourseDto courseDto) {
        try {
            Course updatedCourse = courseService.updateCourse(id, courseDto);
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
