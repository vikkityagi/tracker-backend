package com.example.application.tacker.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.application.tacker.enums.ActionType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private LoanApplication application;

    @ManyToOne
    @JoinColumn(name = "action_by")
    private User actionBy;

    @ManyToOne
    @JoinColumn(name = "workflow_step_id")
    private WorkFlowStep workflowStep;

    @Enumerated(EnumType.STRING)
    private ActionType action;

    private String remarks;

    private LocalDateTime actionDate;
}
