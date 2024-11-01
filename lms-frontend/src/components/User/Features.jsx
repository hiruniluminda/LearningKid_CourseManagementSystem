// src/components/Features.js
import React from 'react';
import './Features.css'

const Features = () => {
  return (
    <section className="features">
      <div className="container">
        <div className="row">
          <div className="col-lg-4 col-12">
            <div className="features-post">
              <div className="features-content">
                <div className="content-show">
                  <h4><i className="fa fa-pencil"></i>All Courses</h4>
                </div>
                <div className="content-hide">
                  <p>A course description outlines to prospective students what the course is about, the topics that will be covered and why, and often details how the learning will occur through assignments, assessments and required materials.</p>
                  <p className="hidden-sm">Curabitur id eros vehicula, tincidunt libero eu, lobortis mi. In mollis eros a posuere imperdiet.</p>
                  <div className="scroll-to-section"><a href="#section2">More Info.</a></div>
                </div>
              </div>
            </div>
          </div>
          {/* Repeat for other features */}
        </div>
      </div>
    </section>
  );
};

export default Features;
