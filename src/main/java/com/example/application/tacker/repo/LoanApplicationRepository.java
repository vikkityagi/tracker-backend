package com.example.application.tacker.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.application.tacker.entity.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    // @Query("SELECT la FROM LoanApplication la WHERE la.userId = :userId AND la.nextStep = :stepOrder")
    // List<LoanApplication> findAllByUserIdAndStepOrder(Long userId, Long stepOrder);

    @Query("SELECT la FROM LoanApplication la WHERE la.email = :email")
    List<LoanApplication> findAllByEmail(String email);

    @Query("SELECT la FROM LoanApplication la WHERE la.email = :email AND la.nextStep = :stepOrder")
    List<LoanApplication> findAllByEmailAndStepOrder(String email, String stepOrder);

    @Query("SELECT la FROM LoanApplication la WHERE la.nextStep = :stepOrder")
    List<LoanApplication> findAllStepOrder(Long stepOrder);


}