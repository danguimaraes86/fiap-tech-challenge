package com.example.techChallengeParkimetro.infra.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Getter
@NoArgsConstructor
public class DefaultError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private Map<String, String> message;
    private String path;

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
