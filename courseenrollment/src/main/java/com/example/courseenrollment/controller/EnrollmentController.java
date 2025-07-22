package com.example.courseenrollment.controller;

import com.example.courseenrollment.dto.EnrollmentRequest;
import com.example.courseenrollment.model.Enrollment;
import com.example.courseenrollment.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<Enrollment> enrollStudent(@Valid @RequestBody EnrollmentRequest request) {
        Enrollment enrollment = enrollmentService.enrollStudent(request.getStudentName(), request.getCourseId());
        return ResponseEntity.ok(enrollment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollment(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollment(id);
        return ResponseEntity.ok(enrollment);
    }
}