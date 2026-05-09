package com.example.application.tacker.dto;

import lombok.Data;

@Data
public class ActionRequestDto {

    private Long applicationId;

    private String action;

    private String remarks;

    private Long officerId;
}