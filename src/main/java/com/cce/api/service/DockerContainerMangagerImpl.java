package com.cce.api.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class DockerContainerMangagerImpl implements DockerContainerManager{

    private final DockerClient dockerClient;

    @Autowired
    public DockerContainerMangagerImpl(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Override
    public String executeCodeInContainer(String code) {
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

            ExecCreateCmdResponse execResponse = dockerClient.execCreateCmd(container.getId())
                    .withAttachStdout(true)
                    .withAttachStderr(true)
                    .withCmd("/app/run.sh")
                    .exec();

            StringBuilder outputBuilder = new StringBuilder();
            dockerClient.execStartCmd(execResponse.getId()).exec(new ExecStartResultCallback() {
                @Override
                public void onNext(Frame item) {
                    outputBuilder.append(new String(item.getPayload()));
                }
            }).awaitCompletion();

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
        return output;
    }
}
