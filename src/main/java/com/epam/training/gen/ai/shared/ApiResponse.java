package com.epam.training.gen.ai.shared;

import org.springframework.http.ResponseEntity;

public interface ApiResponse {
  public ResponseEntity<?> asResponseEntity();
}
