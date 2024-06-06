package com.swiftdeploy.swiftdeploy.Repositories;

import com.swiftdeploy.swiftdeploy.Models.ProjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<ProjectModel, UUID> {
}
