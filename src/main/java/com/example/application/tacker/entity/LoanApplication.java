package com.example.application.tacker.entity;


import com.example.application.tacker.enums.ApplicationStatus;
import com.example.application.tacker.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String name;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private Role role;

    // @ManyToOne
    // @JoinColumn(name = "applicant_id")
    // private User applicant;

    @ManyToOne
    @JoinColumn(name = "current_step_id")
    private WorkFlowStep currentStep;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String purpose;

    private String email;

    private int nextStep;
}
