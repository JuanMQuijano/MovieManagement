package org.jquijano.proyects.MovieManagement.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        @JsonProperty("http_code")
        int httpCode,
        @JsonProperty("url")
        String urlError,
        @JsonProperty("http_method")
        String httpMethod,
        String message,
        @JsonProperty("backend_message")
        String backendMessage,
        LocalDateTime timestamp,
        List<String> details
) {
}
