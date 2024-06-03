package com.cce.api.dto;

import com.cce.api.entity.Language;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeInput {
    private String submissionId;
    private Language lang;
    private String code;
    private String inputs;
    private String testCases;
}
