package com.swiftdeploy.swiftdeploy.Repositories;

import com.swiftdeploy.swiftdeploy.Models.DeploymentModel;
import com.swiftdeploy.swiftdeploy.Models.ProjectModel;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;


public interface DeploymentRepository extends JpaRepository<DeploymentModel, UUID> {
    DeploymentModel findByProject(ProjectModel project);
}
