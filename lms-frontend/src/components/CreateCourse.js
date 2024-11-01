import React, { useState } from "react";
import axios from "axios";

const CreateCourse = () => {
  const [course, setCourse] = useState({ title: "", description: "", teacher: { id: "" } });
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setSuccessMessage("");
    setErrorMessage("");

    axios.post("http://localhost:8080/api/courses/create", course)
    
      .then((response) => {
        setSuccessMessage("Course created successfully");
        setCourse({ title: "", description: "", teacher: { id: "" } });
      })
      .catch((error) => {
        setErrorMessage("Error creating course: " + error.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Title</label>
        <input
          type="text"
          value={course.title}
          onChange={(e) => setCourse({ ...course, title: e.target.value })}
          disabled={loading}
        />
      </div>
      <div>
        <label>Description</label>
        <textarea
          value={course.description}
          onChange={(e) =>
            setCourse({ ...course, description: e.target.value })
          }
          disabled={loading}
        ></textarea>
      </div>
      <div>
        <label>Teacher ID</label>
        <input
          type="text"
          value={course.teacher.id}
          onChange={(e) => setCourse({ ...course, teacher: { id: e.target.value } })}
          disabled={loading}
        />
      </div>
      <button type="submit" disabled={loading}>
        {loading ? "Creating..." : "Create Course"}
      </button>
      {successMessage && <p style={{ color: "green" }}>{successMessage}</p>}
      {errorMessage && <p style={{ color: "red" }}>{errorMessage}</p>}
    </form>
  );
};

export default CreateCourse;
