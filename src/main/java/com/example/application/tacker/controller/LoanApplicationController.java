package com.example.application.tacker.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.application.tacker.dto.ActionRequestDto;
import com.example.application.tacker.entity.ApplicationHistory;
import com.example.application.tacker.entity.LoanApplication;
import com.example.application.tacker.entity.User;
import com.example.application.tacker.repo.LoanApplicationRepository;
import com.example.application.tacker.repo.UserRepository;
import com.example.application.tacker.service.LoanApplicationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
@CrossOrigin
public class LoanApplicationController {

    
    private final LoanApplicationService service;
    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepo;

    @PostMapping
    public LoanApplication createApplication(
            @RequestBody LoanApplication application) {
        return service.createApplication(application);
    }

    // test
    @PostMapping("/action")
    public String takeAction(
            @RequestBody ActionRequestDto dto) {
        return service.takeAction(dto);
    }

    @GetMapping("/tracking/{applicationId}")
    public List<ApplicationHistory> getTracking(
            @PathVariable Long applicationId) {
        return service.getTracking(applicationId);
    }

    @GetMapping("/loanapplications/{userId}")
    public List<LoanApplication> getAllApplicationsOfApplicant(@PathVariable Long userId) {
        Optional<User> user = this.userRepo.findById(userId);
        if(!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        
            return loanApplicationRepository.findAllByEmail(user.get().getEmail());
        

        
        // return loanApplicationRepository.findAllByUserIdAndStepOrder(user.get().getEmail(), stepOrder);
    }

    @GetMapping("/loanapplications/{userId}/{stepOrder}")
    public List<LoanApplication> getAllApplications(@PathVariable Long userId, @PathVariable Long stepOrder) {
        Optional<User> user = this.userRepo.findById(userId);
        if(!user.isPresent()) {
            throw new RuntimeException("User not found");
        }

        if(stepOrder == null) {
            return loanApplicationRepository.findAllByEmail(user.get().getEmail());
        }

        
        return loanApplicationRepository.findAllStepOrder(stepOrder);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        
        User res = service.login(user);
        if(res != null) {
            return res;
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }
}
