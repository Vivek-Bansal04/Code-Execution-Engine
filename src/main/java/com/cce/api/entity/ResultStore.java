package com.cce.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Getter
@Setter
@Document(collection = "result_store")
public class ResultStore {
    @Id
    private UUID id;
    private String submissionId; //questionId + userId
    private Language language;
    private String output;
//    private String executionTime;
//  private String input
//  private String questionId
}
