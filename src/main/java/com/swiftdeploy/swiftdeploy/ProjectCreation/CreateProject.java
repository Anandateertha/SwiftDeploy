package com.swiftdeploy.swiftdeploy.ProjectCreation;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swiftdeploy.swiftdeploy.Authentication.AuthService;
import com.swiftdeploy.swiftdeploy.Models.ProjectModel;
import com.swiftdeploy.swiftdeploy.Models.ResponseMessage;
import com.swiftdeploy.swiftdeploy.Repositories.ProjectRepository;
import com.swiftdeploy.swiftdeploy.Repositories.UserRepository;

@Service
public class CreateProject {

        @Autowired
        private AuthService authService;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ProjectRepository projectRepository;

        public ResponseEntity<ResponseMessage> createProject(ProjectModel project, String token) {

                ResponseMessage responseMessage = new ResponseMessage();

                try {
                        UUID userId = userRepository.findByEmail(authService.verifyToken(token)).getUserId();

                        project.setUserId(userId);
                        project.setCreatedAt(LocalDate.now());
                        projectRepository.save(project);

                        responseMessage.setSuccess(true);
                        responseMessage.setMessage("Project created successfully!");
                        return ResponseEntity.status(200).body(responseMessage);
                } catch (Exception e) {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
                        return ResponseEntity.status(500).body(responseMessage);
                }
        }

        public ResponseEntity<Object> fetchAllProjects(String token) {
                ResponseMessage responseMessage = new ResponseMessage();
                try {
                        String email = authService.verifyToken(token);
                        UUID userID = userRepository.findByEmail(email).getUserId();
                        return ResponseEntity.status(200).body(projectRepository.findByUserId(userID));
                } catch (Exception e) {
                        responseMessage.setSuccess(false);
                        responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
                        return ResponseEntity.status(500).body(responseMessage);
                }
        }
}
