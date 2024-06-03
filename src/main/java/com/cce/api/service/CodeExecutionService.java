package com.cce.api.service;

import com.cce.api.dto.CodeInput;

public interface CodeExecutionService {
    void executeCode(CodeInput code);
    String testCodeExecution(String code);
}
