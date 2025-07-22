package com.example.courseenrollment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequest {
    @NotBlank(message = "Student name is required")
    private String studentName;
    
    @NotNull(message = "Course ID is required")
    private Long courseId;
}