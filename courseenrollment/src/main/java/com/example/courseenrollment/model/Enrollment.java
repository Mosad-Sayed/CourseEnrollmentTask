package com.example.courseenrollment.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String studentName;
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}