package com.epam.training.gen.ai.chat.api.controller;

import com.epam.training.gen.ai.chat.api.model.response.ChatResponse;
import com.epam.training.gen.ai.chat.api.model.response.ChatResponse.ChatErrorResponse;
import com.epam.training.gen.ai.chat.api.model.response.ChatResponse.ChatSuccessfulResponse;
import com.epam.training.gen.ai.chat.service.CompletionInvocationService;
import com.epam.training.gen.ai.document.reader.ContentType;
import com.epam.training.gen.ai.embedding.VectorStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequestMapping("/api/chat")
@RestController
public class ChatController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

  private final CompletionInvocationService completionService;
  private final VectorStorageService vectorStorageService;

  @Autowired
  public ChatController(CompletionInvocationService completionService,
                        VectorStorageService vectorStorageService) {
    this.completionService = completionService;
    this.vectorStorageService = vectorStorageService;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getChatResponse(@RequestParam(value = "prompt") String prompt) {
    final var response = this.completionService.complete(prompt);

    LOGGER.info("-> Received completion request for prompt: {}", prompt);
    LOGGER.info("<- Generated response: {}", response);

    return response
      .map(output -> (ChatResponse) new ChatSuccessfulResponse(output))
      .orElseGet(() -> new ChatErrorResponse("Failed to perform chat completion"))
      .asResponseEntity();
  }

  @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> searchDocuments(
    @RequestParam(value = "query") String query,
    @RequestParam(value = "topK", defaultValue = "10") Integer topK) {

    var documents = this.vectorStorageService.search(query, topK);

    LOGGER.info("-> Received search request for query: {} with topK: {}", query, topK);
    LOGGER.info("<- Found {} documents", documents.size());

    return ResponseEntity.ok(documents);
  }

  @PostMapping(
    value = "/upload",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
    var contentType = ContentType.infer(file.getContentType())
      .orElseThrow(() -> new IllegalArgumentException("Invalid file."));

    var filename = file.getOriginalFilename();

    LOGGER.info("-> Starting embedding of the file: {}", filename);
    this.vectorStorageService.persistDocument(file.getInputStream(), contentType);
    LOGGER.info("<- Finished embedding of the file: {}", filename);

    return ResponseEntity.ok("File uploaded successfully.");
  }
}