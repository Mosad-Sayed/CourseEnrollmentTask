package com.example.courseenrollment.service;

import com.example.courseenrollment.model.Enrollment;
import org.springframework.context.ApplicationEvent;

public class EnrollmentEvent extends ApplicationEvent {
    private final Enrollment enrollment;

    public EnrollmentEvent(Object source, Enrollment enrollment) {
        super(source);
        this.enrollment = enrollment;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }
}