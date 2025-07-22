package com.example.courseenrollment.service;

import com.example.courseenrollment.exception.CourseFullException;
import com.example.courseenrollment.exception.NotFoundException;
import com.example.courseenrollment.model.Course;
import com.example.courseenrollment.model.Enrollment;
import com.example.courseenrollment.repository.CourseRepository;
import com.example.courseenrollment.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                            CourseRepository courseRepository,
                            ApplicationEventPublisher eventPublisher) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Enrollment enrollStudent(String studentName, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        if (course.getEnrolledCount() >= course.getCapacity()) {
            throw new CourseFullException("Course is full");
        }

        // Increment enrolled count atomically
        course.setEnrolledCount(course.getEnrolledCount() + 1);
        courseRepository.save(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentName(studentName);
        enrollment.setCourse(course);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        // Publish enrollment event
        eventPublisher.publishEvent(new EnrollmentEvent(this, savedEnrollment));

        return savedEnrollment;
    }

    public Enrollment getEnrollment(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment not found"));
    }
}