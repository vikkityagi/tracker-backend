package com.example.application.tacker.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.application.tacker.dto.ActionRequestDto;
import com.example.application.tacker.entity.ApplicationHistory;
import com.example.application.tacker.entity.LoanApplication;
import com.example.application.tacker.entity.User;
import com.example.application.tacker.entity.WorkFlowStep;
import com.example.application.tacker.enums.ActionType;
import com.example.application.tacker.enums.ApplicationStatus;
import com.example.application.tacker.enums.Role;
import com.example.application.tacker.repo.ApplicationHistoryRepository;
import com.example.application.tacker.repo.LoanApplicationRepository;
import com.example.application.tacker.repo.UserRepository;
import com.example.application.tacker.repo.WorkFlowStepRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final WorkFlowStepRepository workflowStepRepository;
    private final ApplicationHistoryRepository historyRepository;
    private final UserRepository userRepository;

    public LoanApplication createApplication(LoanApplication application) {

        User applicant = userRepository
                .findById(application.getUserId())
                .orElseThrow();

        if(!applicant.getEmail().equals(application.getEmail())) {
            throw new RuntimeException("Only Valid Applicants can create applications");
        }

        WorkFlowStep firstStep = workflowStepRepository
                .findByStepOrder(1)
                .orElseThrow();

        application.setRole(Role.APPLICANT);
        application.setCurrentStep(firstStep);
        application.setStatus(ApplicationStatus.IN_PROGRESS);
        application.setEmail(applicant.getEmail());
        application.setNextStep(firstStep.getStepOrder() + 1);

        return loanApplicationRepository.save(application);
    }

    public String takeAction(ActionRequestDto dto) {


        // Fetch application data
        LoanApplication application = loanApplicationRepository
                .findById(dto.getApplicationId())
                .orElseThrow();

        User officer = userRepository
                .findById(dto.getOfficerId())
                .orElseThrow();

        Integer currentStepOrder = application
                .getCurrentStep()
                .getStepOrder();

        WorkFlowStep nextStep;

        if(dto.getAction().equalsIgnoreCase("APPROVED")) {

            nextStep = workflowStepRepository
                    .findByStepOrder(currentStepOrder + 1)
                    .orElse(null);

            if(nextStep != null) {

                application.setCurrentStep(nextStep);
                application.setStatus(ApplicationStatus.IN_PROGRESS);
                application.setNextStep(application.getNextStep()+1);

            } else {

                application.setStatus(ApplicationStatus.APPROVED);
            }

        } else {

            Integer previousStepOrder = currentStepOrder - 1;

            WorkFlowStep previousStep = workflowStepRepository
                    .findByStepOrder(previousStepOrder)
                    .orElse(application.getCurrentStep());

            application.setCurrentStep(previousStep);
            application.setStatus(ApplicationStatus.REJECTED);
            application.setNextStep(application.getNextStep());
        }

        loanApplicationRepository.save(application);

        ApplicationHistory history = new ApplicationHistory();

        history.setApplication(application);
        history.setActionBy(officer);
        history.setWorkflowStep(application.getCurrentStep());
        history.setRemarks(dto.getRemarks());
        history.setActionDate(LocalDateTime.now());

        if(dto.getAction().equalsIgnoreCase("APPROVED")) {
            history.setAction(ActionType.APPROVED);
        } else {
            history.setAction(ActionType.REJECTED);
        }

        historyRepository.save(history);

        return "Action Completed";
    }

    public List<ApplicationHistory> getTracking(Long applicationId) {

        return historyRepository.findByApplicationId(applicationId);
    }

    public User login(User user) {

        User existingUser = userRepository
                .findByEmail(user.getEmail())
                .orElseThrow();

        if(existingUser.getPassword().equals(user.getPassword())) {
            return existingUser;
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }
}