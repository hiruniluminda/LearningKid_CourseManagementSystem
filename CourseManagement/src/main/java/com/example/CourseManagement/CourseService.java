package com.example.CourseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LessonRepository lessonRepository;

    // Convert Course entity to CourseDto
    public CourseDto convertToDto(Course course) {
        List<LessonDto> lessonDtos = course.getLessons().stream()
                .map(lesson -> new LessonDto(
                        lesson.getLessonId(),
                        lesson.getCourse(),
                        lesson.getLessonTitle(),
                        lesson.getVideoUrl(),
                        lesson.getNotes(),
                        lesson.isCompleted(),
                        lesson.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return new CourseDto(
                course.getId(),
                course.getTitle(),
                course.getPrice(),
                course.getReview(),
                course.getDescription(),
                course.getLesson(),
                course.getStudent(),
                course.getDuration(),
                course.getImage(),
                lessonDtos
        );
    }

    // Convert CourseDto to Course entity
    public Course convertToEntity(CourseDto courseDto) {
        List<Lesson> lessons = courseDto.getLessons().stream()
                .map(lessonDto -> {
                    Course course = new Course(courseDto.getId(), courseDto.getTitle(),
                            courseDto.getPrice(), courseDto.getReview(),
                            courseDto.getDescription(), courseDto.getLesson(),
                            courseDto.getStudent(), courseDto.getDuration(),
                            courseDto.getImage(), null);
                    return new Lesson(
                            lessonDto.getLessonId(),
                            course,
                            lessonDto.getLessonTitle(),
                            lessonDto.getVideoUrl(),
                            lessonDto.getNotes(),
                            lessonDto.isCompleted(),
                            lessonDto.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());

        return new Course(
                courseDto.getId(),
                courseDto.getTitle(),
                courseDto.getPrice(),
                courseDto.getReview(),
                courseDto.getDescription(),
                courseDto.getLesson(),
                courseDto.getStudent(),
                courseDto.getDuration(),
                courseDto.getImage(),
                lessons
        );
    }

    // Get all courses and return them as DTOs
    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    // Get a course by its ID
    public Course getCourseById(int courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isPresent()) {
            return courseOptional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }

    // Add a new course
    public Course addCourse(CourseDto courseDto) {
        Course course = convertToEntity(courseDto);
        return courseRepository.save(course);
    }

    // Add a lesson to a course
    public Lesson addLessonToCourse(int courseId, LessonDto lessonDto) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (!courseOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }

        Course course = courseOptional.get();
        Lesson lesson = new Lesson(
                lessonDto.getLessonId(),
                course,  // Set the course reference
                lessonDto.getLessonTitle(),
                lessonDto.getVideoUrl(),
                lessonDto.getNotes(),
                lessonDto.isCompleted(),
                lessonDto.getCreatedAt()
        );

        return lessonRepository.save(lesson);
    }

    // Delete a course by ID
    public void deleteCourse(int courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        courseRepository.deleteById(courseId);
    }

    // Update an existing course
    public Course updateCourse(int id, CourseDto courseDto) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();

            existingCourse.setTitle(courseDto.getTitle());
            existingCourse.setDescription(courseDto.getDescription());
            existingCourse.setPrice(courseDto.getPrice());
            existingCourse.setReview(courseDto.getReview());
            existingCourse.setLesson(courseDto.getLesson());
            existingCourse.setStudent(courseDto.getStudent());
            existingCourse.setDuration(courseDto.getDuration());
            existingCourse.setImage(courseDto.getImage());

            return courseRepository.save(existingCourse);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }
}
