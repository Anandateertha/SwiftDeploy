package com.swiftdeploy.swiftdeploy.ProjectCreation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swiftdeploy.swiftdeploy.Authentication.AuthService;
import com.swiftdeploy.swiftdeploy.Models.DeploymentModel;
import com.swiftdeploy.swiftdeploy.Models.ProjectModel;
import com.swiftdeploy.swiftdeploy.Models.ResponseMessage;
import com.swiftdeploy.swiftdeploy.Repositories.DeploymentRepository;
import com.swiftdeploy.swiftdeploy.Repositories.ProjectRepository;
import com.swiftdeploy.swiftdeploy.Repositories.UserRepository;
import com.swiftdeploy.swiftdeploy.StaticInformations.ECSClientService;

import software.amazon.awssdk.services.ecs.model.AssignPublicIp;
import software.amazon.awssdk.services.ecs.model.AwsVpcConfiguration;
import software.amazon.awssdk.services.ecs.model.ContainerOverride;
import software.amazon.awssdk.services.ecs.model.KeyValuePair;
import software.amazon.awssdk.services.ecs.model.LaunchType;
import software.amazon.awssdk.services.ecs.model.NetworkConfiguration;
import software.amazon.awssdk.services.ecs.model.RunTaskRequest;
import software.amazon.awssdk.services.ecs.model.TaskOverride;

@Service
public class DeployProject {

        @Autowired
        private DeploymentRepository deploymentRepository;

        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AuthService authService;

        public ResponseEntity<ResponseMessage> createDeployService(String projectId, String token) {

                ResponseMessage responseMessage = new ResponseMessage();
                try {
                        DeploymentModel deployment = new DeploymentModel();

                        ProjectModel project = projectRepository.findById(UUID.fromString(projectId)).orElse(null);
                        if (project == null) {
                                responseMessage.setMessage("Project not found");
                                return ResponseEntity.status(404).body(responseMessage);
                        }

                        UUID randomId = UUID.randomUUID();
                        UUID id = randomId;
                        deployment.setDeploymentId(id);
                        deployment.setProject(project);
                        deployment.setCreatedAt(LocalDate.now());

                        List<KeyValuePair> envVariables = new ArrayList<>();
                        envVariables.add(
                                        KeyValuePair.builder().name("BUCKET_NAME").value(System.getenv("BUCKET_NAME"))
                                                        .build());

                        envVariables.add(KeyValuePair.builder().name("AWS_ACCESS_KEY_ID")
                                        .value(System.getenv("AWS_ACCESS_KEY"))
                                        .build());

                        envVariables.add(KeyValuePair.builder().name("AWS_SECRET_ACCESS_KEY")
                                        .value(System.getenv("AWS_SECRET_KEY")).build());

                        envVariables.add(KeyValuePair.builder().name("GIT_URL")
                                        .value(projectRepository.findByProjectId(UUID.fromString(projectId)).getGitURL())
                                        .build());

                        envVariables.add(KeyValuePair.builder().name("USER_ID")
                                        .value(userRepository.findByEmail(authService.verifyToken(token)).getUserId()
                                                        .toString())
                                        .build());

                        envVariables.add(KeyValuePair.builder().name("PROJECT_ID")
                                        .value(projectId).build());

                        envVariables.add(KeyValuePair.builder().name("DEPLOYMENT_ID")
                                        .value(id.toString()).build());

                        String existingTaskDefinitionArn = "arn:aws:ecs:" + System.getenv("REGION") + ":"
                                        + System.getenv("ACCOUNT_ID") + ":task-definition/"
                                        + System.getenv("TASK_DEFINITION_NAME");

                        ContainerOverride containerOverride = ContainerOverride.builder()
                                        .name(System.getenv("IMAGE_NAME"))
                                        .environment(envVariables)
                                        .build();

                        TaskOverride taskOverride = TaskOverride.builder()
                                        .containerOverrides(containerOverride)
                                        .build();

                        String[] subnets = System.getenv("SUBNETS").split(",");

                        RunTaskRequest runTaskRequest = RunTaskRequest.builder()
                                        .cluster(System.getenv("CLUSTER_NAME"))
                                        .taskDefinition(existingTaskDefinitionArn)
                                        .overrides(taskOverride)
                                        .networkConfiguration(NetworkConfiguration.builder()
                                                        .awsvpcConfiguration(AwsVpcConfiguration.builder()
                                                                        .subnets(subnets)
                                                                        .assignPublicIp(AssignPublicIp.ENABLED)
                                                                        .build())
                                                        .build())
                                        .launchType(LaunchType.FARGATE)
                                        .build();

                        ECSClientService.ecsClient.runTask(runTaskRequest);

                        deploymentRepository.save(deployment);

                        responseMessage.setSuccess(true);
                        responseMessage.setMessage("Deployment created successfully");
                        return ResponseEntity.status(200).body(responseMessage);

                } catch (Exception e) {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
                        return ResponseEntity.status(500).body(responseMessage);
                }
        }
}
