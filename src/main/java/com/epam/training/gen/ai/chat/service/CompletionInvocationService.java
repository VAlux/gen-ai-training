package com.epam.training.gen.ai.chat.service;

import com.epam.training.gen.ai.config.properties.RagConfigurationProperties;
import com.epam.training.gen.ai.embedding.VectorStorageService;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompletionInvocationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CompletionInvocationService.class);

  private final ChatCompletionService completionService;
  private final InvocationContext invocationContext;
  private final Kernel kernel;
  private final ChatHistoryService historyService;
  private final RagConfigurationProperties ragConfigurationProperties;
  private final VectorStorageService vectorStorageService;

  @Autowired
  public CompletionInvocationService(ChatCompletionService completionService,
                                     InvocationContext invocationContext,
                                     Kernel kernel,
                                     ChatHistoryService historyService,
                                     RagConfigurationProperties ragConfigurationProperties,
                                     VectorStorageService vectorStorageService) {
    this.completionService = completionService;
    this.invocationContext = invocationContext;
    this.kernel = kernel;
    this.historyService = historyService;
    this.ragConfigurationProperties = ragConfigurationProperties;
    this.vectorStorageService = vectorStorageService;
  }

  public Optional<String> complete(String prompt) {
    var similarDocuments = this.vectorStorageService.search(prompt, 5);

    var context = similarDocuments.stream()
      .map(Document::getText)
      .collect(Collectors.joining("\n"));

    var systemPrompt = this.ragConfigurationProperties.getSystemPrompt();
    var fullPrompt = systemPrompt + "\nContext: " + context + "\nUser: " + prompt;

    try {
      var response =
        this.completionService.getChatMessageContentsAsync(fullPrompt, this.kernel, this.invocationContext).block();

      final var responseContent = Optional
        .ofNullable(response)
        .map(content -> joinMessageContent(content, "\n"));

      responseContent.ifPresent(this.historyService::addSystemMessage);

      return responseContent;
    } catch (Exception ex) {
      LOGGER.error("Error communicating with completion API: {}", ex.getMessage(), ex);
      return Optional.empty();
    }
  }

  private static String joinMessageContent(Collection<ChatMessageContent<?>> messageContent, String delimiter) {
    return messageContent.stream().map(ChatMessageContent::getContent).collect(Collectors.joining(delimiter));
  }
}
