package com.swiftdeploy.swiftdeploy.Models;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ResponseMessage {
    private boolean success;
    private String message;
}
