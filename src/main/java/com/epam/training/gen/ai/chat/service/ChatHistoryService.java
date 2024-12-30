package com.epam.training.gen.ai.chat.service;

import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import org.springframework.stereotype.Service;

@Service
public class ChatHistoryService {

  private final ChatHistory chatHistory;

  public ChatHistoryService() {
    this.chatHistory = new ChatHistory();
  }

  public void addUserMessage(String message) {
    this.chatHistory.addUserMessage(message);
  }

  public void addSystemMessage(String message) {
    this.chatHistory.addSystemMessage(message);
  }

  public ChatHistory getChatHistory() {
    return this.chatHistory;
  }
}