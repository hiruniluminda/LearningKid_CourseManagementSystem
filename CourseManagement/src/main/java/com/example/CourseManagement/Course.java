package com.example.CourseManagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private double price;
    private String review;
    private String description;
    private String lesson;
    private String student;
    private String duration;
    private String image;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Lesson> lessons;

    public Course(Course course) {
        this.id = course.id;
        this.title = course.title;
        this.price = course.price;
        this.review = course.review;
        this.description = course.description;
        this.student = course.student;
        this.duration = course.duration;
        this.image = course.image;
        this.lessons = course.lessons; // Copy the lessons list
    }
}
