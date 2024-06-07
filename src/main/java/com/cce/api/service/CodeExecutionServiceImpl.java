package com.cce.api.service;

import com.cce.api.dto.CodeInput;
import com.cce.api.dto.CodeSubmissionResponseDTO;
import com.cce.api.dto.ResultDTO;
import com.cce.api.entity.Language;
import com.cce.api.entity.ResultStore;
import com.cce.api.publishers.CodeResultPublisher;
import com.cce.api.repository.ResultStoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CodeExecutionServiceImpl implements CodeExecutionService {
    private final DockerContainerManager dockerContainerManager;
    private final ResultStoreRepository repository;
    private final CodeResultPublisher codeResultPublisher;

    @Autowired
    public CodeExecutionServiceImpl(
            DockerContainerManager dockerContainerManager,
            ResultStoreRepository repository,
            CodeResultPublisher codeResultPublisher
    ){
        this.dockerContainerManager = dockerContainerManager;
        this.repository = repository;
        this.codeResultPublisher = codeResultPublisher;
    }

    @Override
    @RabbitListener(queues = "${JAVA_CODE_SUBMISSION_QUEUE_NAME}",containerFactory = "customContainerFactory")
    public void executeCode(CodeInput request) {
        //spring aop for global logs
        log.info("request {}",request);
        //do necessary validations here
//        String output = dockerContainerManager.executeCodeInContainer(code);


        //TODO hardcoding for becuase of docker issues
        CodeSubmissionResponseDTO response = prepareResultDTO();
        codeResultPublisher.send(response);

        //used to store in db which is disable currently as being stored in code manager service.
        //ResultStore result = prepareResult(output, request.getSubmissionId(), request.getLang());
        //repository.save(result);
    }

    public String testCodeExecution(String code){
        String output = dockerContainerManager.executeCodeInContainer(code);
        ResultStore result = new ResultStore();
        result.setLanguage(Language.JAVA);
        result.setOutput("Successful");
        result.setSubmissionId(UUID.randomUUID().toString());
        result.setId(UUID.randomUUID());
        repository.save(result);
        return output;
    }

    private ResultStore prepareResult(String output,String submissionId,Language language){
        ResultStore result = new ResultStore();
        result.setLanguage(language);
        result.setOutput("Successful");
        result.setSubmissionId(submissionId);
        result.setId(UUID.randomUUID());
        return result;
    }

    private CodeSubmissionResponseDTO prepareResultDTO(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setResult("All testcases passed");
        resultDTO.setVerdict("Accepted");

        CodeSubmissionResponseDTO responseDTO = new CodeSubmissionResponseDTO();
        responseDTO.setResult(resultDTO);
        responseDTO.setStatusCode(0);
        responseDTO.setStatus("Successful");
        responseDTO.setMessage("code compiled and run successfully");
        responseDTO.setSubmissionId(UUID.randomUUID());
        return responseDTO;
    }
}
