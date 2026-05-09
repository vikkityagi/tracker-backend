package com.example.application.tacker.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.tacker.entity.ApplicationHistory;

import java.util.List;

public interface ApplicationHistoryRepository extends JpaRepository<ApplicationHistory, Long> {

    List<ApplicationHistory> findByApplicationId(Long applicationId);
}
