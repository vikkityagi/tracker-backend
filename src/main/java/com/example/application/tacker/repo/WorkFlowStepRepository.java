package com.example.application.tacker.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.tacker.entity.WorkFlowStep;

import java.util.Optional;

public interface WorkFlowStepRepository extends JpaRepository<WorkFlowStep, Long> {

    Optional<WorkFlowStep> findByStepOrder(Integer stepOrder);
}
