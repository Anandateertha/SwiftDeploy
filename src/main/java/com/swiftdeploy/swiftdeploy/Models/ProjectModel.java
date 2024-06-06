package com.swiftdeploy.swiftdeploy.Models;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ProjectModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID projectId;

    private UUID userId;

    private String projectName;

    private String gitURL;

    private String subDomain;

    private String customDomain;

    private LocalDate createdAt;
}
