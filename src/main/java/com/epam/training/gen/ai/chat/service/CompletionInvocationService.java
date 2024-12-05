package com.epam.training.gen.ai.chat.service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  @Autowired
  public CompletionInvocationService(ChatCompletionService completionService,
                                     InvocationContext invocationContext,
                                     Kernel kernel) {
    this.completionService = completionService;
    this.invocationContext = invocationContext;
    this.kernel = kernel;
  }


  public Optional<String> complete(String prompt) {
    try {
      var response =
        this.completionService.getChatMessageContentsAsync(prompt, this.kernel, this.invocationContext).block();

      return Optional.ofNullable(response).map(content -> joinMessageContent(content, "\n"));
    } catch (Exception ex) {
      LOGGER.error("Error communicating with completion API: {}", ex.getMessage(), ex);
      return Optional.empty();
    }
  }

  private static String joinMessageContent(Collection<ChatMessageContent<?>> messageContent, String delimiter) {
    return messageContent.stream().map(ChatMessageContent::getContent).collect(Collectors.joining(delimiter));
  }
}
