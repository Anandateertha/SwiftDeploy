package com.swiftdeploy.swiftdeploy.Repositories;

import com.swiftdeploy.swiftdeploy.Models.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;



public interface ProjectRepository extends JpaRepository<ProjectModel, UUID> {
    ProjectModel findByProjectId(UUID projectId);
    List<ProjectModel> findByUserId(UUID userId);
}
