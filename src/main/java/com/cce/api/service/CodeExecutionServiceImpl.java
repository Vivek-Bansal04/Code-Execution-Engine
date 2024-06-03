package com.cce.api.service;

import com.cce.api.entity.Language;
import com.cce.api.entity.ResultStore;
import com.cce.api.repository.ResultStoreRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {
    private final DockerContainerManager dockerContainerManager;
    private final ResultStoreRepository repository;

    @Autowired
    public CodeExecutionServiceImpl(
            DockerContainerManager dockerContainerManager,
            ResultStoreRepository repository){
        this.dockerContainerManager = dockerContainerManager;
        this.repository = repository;
    }

    @Override
    @RabbitListener(queues = "${CODE_SUBMISSION_QUEUE_NAME}",containerFactory = "customContainerFactory")
    public String executeCode(String code) {
        //do necessary validations here
//        String output = dockerContainerManager.executeCodeInContainer(code);
        String output = "";
        ResultStore result = new ResultStore();
        result.setLanguage(Language.JAVA);
        result.setOutput("Successful");
        result.setSubmissionId(UUID.randomUUID().toString());
        result.setId(UUID.randomUUID());
//        repository.save(result);
        System.out.println(result + " check");
        return output;
    }
}
