package com.swiftdeploy.swiftdeploy.ProjectCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftdeploy.swiftdeploy.Models.ProjectModel;

@RestController
@RequestMapping("api")
public class ProjectController {

    @Autowired
    private CreateProject createProject;

    @PostMapping("createproject")
    public void createproject(@RequestBody ProjectModel project, @RequestHeader String token) {
        createProject.createProject(project, token);
    }
}
