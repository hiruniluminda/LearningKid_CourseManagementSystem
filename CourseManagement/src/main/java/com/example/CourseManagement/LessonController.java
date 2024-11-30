package com.example.CourseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<Lesson> addLesson(@PathVariable int courseId, @RequestBody Lesson lesson) {
        try {
            // Fetch the course by courseId
            Course course = courseService.getCourseById(courseId);

            // If the course doesn't exist, return a NOT_FOUND response
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Associate the course with the lesson
            lesson.setCourse(course);

            // Save the lesson and return it
            Lesson savedLesson = lessonService.saveLesson(lesson);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedLesson);
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @GetMapping("/show/{courseId}")
    public ResponseEntity<List<LessonDto>> getAllLessons(@PathVariable int courseId) {
        List<LessonDto> lessonDtos = lessonService.getLessonsByCourseId(courseId);
        return new ResponseEntity<>(lessonDtos, HttpStatus.OK);
    }


//    @GetMapping
//    public List<LessonDto> getAllLessons() {
//        return lessonService.getAllLessons();
//    }
}
