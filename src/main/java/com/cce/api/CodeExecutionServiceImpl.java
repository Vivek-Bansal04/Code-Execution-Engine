package com.cce.api;

import com.cce.api.service.CodeExecutionService;
import com.cce.api.service.DockerContainerManager;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.UUID;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {
    private final DockerContainerManager dockerContainerManager;

    @Autowired
    public CodeExecutionServiceImpl(
            DockerContainerManager dockerContainerManager
    ){
        this.dockerContainerManager = dockerContainerManager;
    }

    public static String executeCode(String code){
//        return dockerContainerManager.executeCodeInContainer(code);

        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375")
                .build();
        ApacheDockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();

        DockerClient dockerClient = DockerClientBuilder.getInstance(config)
                .withDockerHttpClient(httpClient)
                .build();
        String output;
        try {
            // Create a temporary file to hold the code
            File tempDir = Files.createTempDirectory("JavaCode_" + UUID.randomUUID()).toFile();
            File sourceFile = new File(tempDir, "TempCode.java");

            try (FileWriter writer = new FileWriter(sourceFile)) {
                writer.write(code);
            }

            File localRunScript = new File("run.sh");
            File tempRunScript = new File(tempDir, "run.sh");
            Files.copy(localRunScript.toPath(), tempRunScript.toPath(), StandardCopyOption.REPLACE_EXISTING);
            tempRunScript.setExecutable(true);

            File localInputScript = new File("input.txt");
            File tempInputScript = new File(tempDir, "input.txt");
            Files.copy(localInputScript.toPath(), tempInputScript.toPath(), StandardCopyOption.REPLACE_EXISTING);


            // Pull the Docker image (if not already pulled)
            String imageId = "java-code-executor-image";
//            dockerClient.pullImageCmd(imageId).exec(new PullImageResultCallback()).awaitCompletion();

            // Create a Docker container
            Volume volume = new Volume("/app");
            CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
                    .withVolumes(volume)
                    .withHostConfig(new HostConfig().withBinds(new com.github.dockerjava.api.model.Bind(tempDir.getAbsolutePath(), volume)))
                    .exec();

            // Start the container
            dockerClient.startContainerCmd(container.getId()).exec();

            // Execute the run script
            ExecCreateCmdResponse execResponse = dockerClient.execCreateCmd(container.getId())
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withCmd("/app/run.sh")
                    .exec();

            // Capture the output
            StringBuilder outputBuilder = new StringBuilder();
            dockerClient.execStartCmd(execResponse.getId()).exec(new ExecStartResultCallback() {
                @Override
                public void onNext(Frame item) {
                    outputBuilder.append(new String(item.getPayload()));
                }
            }).awaitCompletion();

            // Stop and remove the container
            // can add this to a queue to stop container
            dockerClient.stopContainerCmd(container.getId()).exec();
            dockerClient.removeContainerCmd(container.getId()).exec();

            // Clean up the temporary file
            String classFilePath = sourceFile.getAbsolutePath().replace(".java", ".class");
            Files.deleteIfExists(Paths.get(classFilePath));
            Files.deleteIfExists(Paths.get(sourceFile.getAbsolutePath()));
            Files.deleteIfExists(Paths.get(tempRunScript.getAbsolutePath()));
            Files.deleteIfExists(Paths.get(tempInputScript.getAbsolutePath()));
            Files.deleteIfExists(Paths.get(tempDir.getAbsolutePath()));

            output = outputBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            output = "Error: " + e.getMessage();
        }
        System.out.println(output);
        return output;
    }

    @Override
    public String executeCode1(String code) {
        return "";
    }
}
