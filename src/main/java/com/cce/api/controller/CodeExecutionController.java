package com.cce.api.controller;

import com.cce.api.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/code")
public class CodeExecutionController {

    private final CodeExecutionService codeExecutionService;

    @Autowired
    public CodeExecutionController(CodeExecutionService codeExecutionService) {
        this.codeExecutionService = codeExecutionService;
    }


    @PostMapping(value = "/execute")
    public String createApprovalRequest(
            @RequestBody String code
    ) {
        //for testing purpose
        return codeExecutionService.executeCode(code);
    }
}
