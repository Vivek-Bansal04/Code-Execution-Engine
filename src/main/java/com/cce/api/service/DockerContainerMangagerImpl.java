package com.cce.api.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class DockerContainerMangagerImpl implements DockerContainerManager{

    @Autowired
    public DockerContainerMangagerImpl() {
    }

    @Override
    public String executeCodeInContainer(String code) {
//        String output;
//        try {
//            // Create a temporary file to hold the code
//            File sourceFile = File.createTempFile("TempCode", ".java");
//            try (FileWriter writer = new FileWriter(sourceFile)) {
//                writer.write(code);
//            }
//
//            // Pull the Docker image (if not already pulled)
//            String imageId = "java-code-executor-image"; // Replace with your Docker image ID
//            dockerClient.pullImageCmd(imageId).exec(new PullImageResultCallback()).awaitCompletion();
//
//            // Create a Docker container
//            Volume volume = new Volume("/app");
//            CreateContainerResponse container = dockerClient.createContainerCmd(imageId)
//                    .withVolumes(volume)
//                    .withHostConfig(new HostConfig().withBinds(new com.github.dockerjava.api.model.Bind(sourceFile.getParent(), volume)))
//                    .exec();
//
//            // Start the container
//            dockerClient.startContainerCmd(container.getId()).exec();
//
//            // Execute the run script
//            ExecCreateCmdResponse execResponse = dockerClient.execCreateCmd(container.getId())
//                    .withAttachStdout(true)
//                    .withAttachStderr(true)
//                    .withCmd("/bin/sh", "/app/run.sh")
//                    .exec();
//
//            // Capture the output
//            StringBuilder outputBuilder = new StringBuilder();
//            dockerClient.execStartCmd(execResponse.getId()).exec(new ExecStartResultCallback() {
//                @Override
//                public void onNext(Frame item) {
//                    outputBuilder.append(new String(item.getPayload()));
//                }
//            }).awaitCompletion();
//
//            // Stop and remove the container
//            dockerClient.stopContainerCmd(container.getId()).exec();
//            dockerClient.removeContainerCmd(container.getId()).exec();
//
//            // Clean up the temporary file
//            Files.deleteIfExists(Paths.get(sourceFile.getAbsolutePath()));
//
//            output = outputBuilder.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            output = "Error: " + e.getMessage();
//        }
//        return output;
        return "";
    }
}
