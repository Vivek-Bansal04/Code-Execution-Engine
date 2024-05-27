package com.cce.api;

import com.cce.api.service.DockerContainerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeExecutionImpl {
    private final DockerContainerManager dockerContainerManager;

    @Autowired
    public CodeExecutionImpl(
            DockerContainerManager dockerContainerManager){
        this.dockerContainerManager = dockerContainerManager;
    }

    public String execute(String code){
        String result = executeCodeInContainer(code);

        return result;
    }

    public String executeCodeInContainer(String code) {
        docker

    }

}
