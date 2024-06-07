package com.swiftdeploy.swiftdeploy.ProjectCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftdeploy.swiftdeploy.Models.ProjectModel;
import com.swiftdeploy.swiftdeploy.Models.ResponseMessage;

@RestController
@RequestMapping("api")
public class ProjectController {

    @Autowired
    private CreateProject createProject;

    @Autowired
    private DeployProject deployProject;

    @PostMapping("createproject")
    public ResponseEntity<ResponseMessage> createproject(@RequestBody ProjectModel project,
            @RequestHeader String token) {
        return createProject.createProject(project, token);
    }

    @PostMapping("/deploy")
    public ResponseEntity<ResponseMessage> deployProject(@RequestHeader String projectId, @RequestHeader String token) {
        return deployProject.createDeployService(projectId, token);
    }

    @GetMapping("/getallprojects")
    public ResponseEntity<Object> getAllProjects(@RequestHeader String token)
    {
        return createProject.fetchAllProjects(token);
    }
}
