package com.epam.training.gen.ai.chat.api.model.response;

import com.epam.training.gen.ai.shared.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public sealed interface ChatResponse extends ApiResponse {

  //@formatter:off
  record ChatSuccessfulResponse(String output) implements ChatResponse {}
  record ChatErrorResponse(String errorMessage) implements ChatResponse {}
  //@formatter:on

  @Override
  default ResponseEntity<?> asResponseEntity() {
    return switch (this) {
      case ChatErrorResponse response ->
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Chat error: " + response.errorMessage);
      case ChatSuccessfulResponse response ->
        ResponseEntity.status(HttpStatus.OK).body(response);
    };
  }
}

