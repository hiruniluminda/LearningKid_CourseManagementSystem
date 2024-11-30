import React, { useState } from "react";
import axios from "axios";

const LessonForm = ({ onLessonAdded }) => {
  const [lessonTitle, setLessonTitle] = useState("");
  const [videoUrl, setVideoUrl] = useState("");
  const [notes, setNotes] = useState("");
  const [courseId, setCourseId] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const lessonData = {
      lessonTitle,
      videoUrl,
      notes,
      courseId,  // Send courseId directly instead of embedding it in a 'course' object
    };

    try {
      const response = await axios.post(`http://localhost:8080/api/courses/${courseId}/lessons`, lessonData);

      alert(response.data); // Display success message from the backend
      setLessonTitle("");
      setVideoUrl("");
      setNotes("");
      setCourseId("");
      if (onLessonAdded) onLessonAdded();
    } catch (error) {
      console.error("Error adding lesson:", error);
      alert("Failed to add lesson. Please try again.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label htmlFor="lessonTitle">Lesson Title:</label>
        <input
          type="text"
          id="lessonTitle"
          value={lessonTitle}
          onChange={(e) => setLessonTitle(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="videoUrl">Video URL:</label>
        <input
          type="url"
          id="videoUrl"
          value={videoUrl}
          onChange={(e) => setVideoUrl(e.target.value)}
          required
        />
      </div>
      <div>
        <label htmlFor="notes">Notes:</label>
        <textarea
          id="notes"
          value={notes}
          onChange={(e) => setNotes(e.target.value)}
          required
        ></textarea>
      </div>
      <div>
        <label htmlFor="courseId">Course ID:</label>
        <input
          type="text"
          id="courseId"
          value={courseId}
          onChange={(e) => setCourseId(e.target.value)}
          required
        />
      </div>
      <button type="submit">Add Lesson</button>
    </form>
  );
};

export default LessonForm;
