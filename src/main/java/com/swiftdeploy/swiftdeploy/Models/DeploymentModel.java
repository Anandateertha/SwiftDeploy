package com.swiftdeploy.swiftdeploy.Models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DeploymentModel {

    @Id
    private UUID deploymentId;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "projectId", nullable = false)
    private ProjectModel project;

    private LocalDate createdAt;
}
