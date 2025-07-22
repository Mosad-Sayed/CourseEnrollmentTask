package com.example.courseenrollment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.courseenrollment.model.Enrollment;

@Component
public class EnrollmentEventListener {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentEventListener.class);

    @EventListener
    public void handleEnrollmentEvent(EnrollmentEvent event) {
        Enrollment enrollment = event.getEnrollment();
        logger.info("Student {} enrolled in course {}", 
            enrollment.getStudentName(), 
            enrollment.getCourse().getTitle());
    }
}