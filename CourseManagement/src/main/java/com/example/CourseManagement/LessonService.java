package com.example.CourseManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Lesson saveLesson(Lesson lesson) {
        if (lesson.getCourse() == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        Course course = courseRepository.findById(lesson.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Lesson lesson1 = new Lesson();
        lesson1.setCourse(course); // Assuming `course` is an existing Course entity

        lesson1.setLessonTitle(lesson.getLessonTitle());
        lesson1.setVideoUrl(lesson.getVideoUrl());
        lesson1.setNotes(lesson.getNotes());

        lessonRepository.save(lesson1);
        return lessonRepository.save(lesson1);
    }

    public void addLesson(LessonDto lessonDto) {
        if (lessonDto.getCourse() == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }

        Course course = courseRepository.findById(lessonDto.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Lesson lesson = new Lesson();
        lesson.setCourse(course); // Assuming `course` is an existing Course entity

        lesson.setLessonTitle(lessonDto.getLessonTitle());
        lesson.setVideoUrl(lessonDto.getVideoUrl());
        lesson.setNotes(lessonDto.getNotes());

        lessonRepository.save(lesson);
    }

    public List<LessonDto> getAllLessons() {
        return lessonRepository.findAll()
                .stream()
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
    }

    public List<LessonDto> getLessonsByCourseId(int courseId) {
        List<Lesson> lessons = lessonRepository.findByCourseId(courseId); // Assuming there's a method like this
        return lessons.stream()
                .map(lesson -> new LessonDto(lesson.getLessonId(),
                        lesson.getCourse(),
                        lesson.getLessonTitle(),
                        lesson.getVideoUrl(),
                        lesson.getNotes(),
                        lesson.isCompleted(),
                        lesson.getCreatedAt())) // Convert to DTO if necessary
                .collect(Collectors.toList());
    }

}
