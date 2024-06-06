package com.swiftdeploy.swiftdeploy.StaticInformations;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ecs.EcsClient;

public class ECSClientService {

    public static final Region region = Region.AP_SOUTH_1;
    public static final EcsClient ecsClient;

    static {
        ecsClient = EcsClient.builder()
                .region(region)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                                System.getenv("AWS_ACCESS_KEY"),
                                System.getenv("AWS_SECRET_KEY"))))
                .build();
    }
}
