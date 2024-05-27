package com.cce.api;

import com.cce.api.service.CodeExecutionService;
import com.cce.api.service.DockerContainerManager;
import com.github.dockerjava.api.DockerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {
    private final DockerContainerManager dockerContainerManager;

    @Autowired
    public CodeExecutionServiceImpl(
            DockerContainerManager dockerContainerManager){
        this.dockerContainerManager = dockerContainerManager;
    }

    @Override
    public String executeCode(String code) {
        //do necessary validations here
        return dockerContainerManager.executeCodeInContainer(code);
    }
}
