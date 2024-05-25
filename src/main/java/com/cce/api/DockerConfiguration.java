package com.cce.api;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerConfiguration {

//    @Bean
//    public DockerHttpClient dockerHttpClient() {
//        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
//
//        return new DockerHttpClient.builder()
//                .dockerHost(config.getDockerHost())
//                .sslConfig(config.getSSLConfig())
//                .local(true)  // Use local transport
//                .build();
//    }

//    @Bean
//    DockerClient dockerClient() {
//        return DockerClientBuilder.getInstance(
//                        DefaultDockerClientConfig.createDefaultConfigBuilder()
//                                .withDockerHost("tcp://localhost:2375")
//                                .build())
//                .build();
//    }
}

