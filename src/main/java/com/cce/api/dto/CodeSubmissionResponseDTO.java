package com.cce.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CodeSubmissionResponseDTO {
    ResultDTO result;
    int statusCode;
    String status;
    String message;
    UUID submissionId;
}
