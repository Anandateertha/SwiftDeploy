package com.swiftdeploy.swiftdeploy.Middlewares;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.swiftdeploy.swiftdeploy.Models.ResponseMessage;

@Service
public class SlugGenerator {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ResponseMessage responseMessage;

    public ResponseEntity<ResponseMessage> reverseProxyMethod() {
        try {
            String proxyURL = System.getenv("SLUG_URL");
            
            @SuppressWarnings("unchecked")
            Map<String, String> response = webClientBuilder.build()
                    .get()
                    .uri(proxyURL)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String slug = response.get("slug");

            responseMessage.setSuccess(true);
            responseMessage.setMessage(slug);
            return ResponseEntity.ok().body(responseMessage);

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMessage);
        }
    }
}
