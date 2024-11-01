import React, { useState, useEffect } from "react";
import axios from "axios";

const CourseList = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);  // To track loading state
  const [error, setError] = useState(null);      // To track error state

  useEffect(() => {
    axios.get("http://localhost:8080/api/courses")
      .then(response => {
        setCourses(response.data);
        setLoading(false);  // Set loading to false when data is loaded
      })
      .catch(error => {
        console.error("Error fetching courses", error);
        setError("Failed to load courses. Please try again.");
        setLoading(false);
      });
  }, []);

  if (loading) {
    return <p>Loading courses...</p>;  // Show loading message while fetching
  }

  if (error) {
    return <p style={{ color: "red" }}>{error}</p>;  // Show error message if any
  }

  return (
    <div>
      <h2>User Panel - Available Courses</h2>
      {courses.length > 0 ? (
        <ul>
          {courses.map(course => (
            <li key={course.id}>
              <strong>{course.title}</strong>: {course.description}
            </li>
          ))}
        </ul>
      ) : (
        <p>No courses available.</p>
      )}
    </div>
  );
};

export default CourseList;
