package com.epam.training.gen.ai.chat.api.controller;

import com.epam.training.gen.ai.chat.api.model.response.ChatResponse;
import com.epam.training.gen.ai.chat.api.model.response.ChatResponse.ChatErrorResponse;
import com.epam.training.gen.ai.chat.api.model.response.ChatResponse.ChatSuccessfulResponse;
import com.epam.training.gen.ai.chat.service.CompletionInvocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/chat")
@RestController
public class ChatController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

  private final CompletionInvocationService completionService;

  @Autowired
  public ChatController(CompletionInvocationService completionService) {
    this.completionService = completionService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getChatResponse(@RequestParam(value = "prompt") String prompt) {
    var response = this.completionService.complete(prompt);

    LOGGER.info("-> Received completion request for prompt: {}", prompt);
    LOGGER.info("<- Generated response: {}", response);

    return response
      .map(output -> (ChatResponse) new ChatSuccessfulResponse(output))
      .orElseGet(() -> new ChatErrorResponse("Failed to perform chat completion"))
      .asResponseEntity();
  }
}
