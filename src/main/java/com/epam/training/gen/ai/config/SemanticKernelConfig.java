package com.epam.training.gen.ai.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.epam.training.gen.ai.properties.OpenAIConfigurationProperties;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelConfig {

  private final OpenAIConfigurationProperties openAIProperties;

  @Autowired
  public SemanticKernelConfig(OpenAIConfigurationProperties openAIProperties) {
    this.openAIProperties = openAIProperties;
  }

  @Bean
  public OpenAIAsyncClient client() {
    return new OpenAIClientBuilder()
      .credential(new AzureKeyCredential(this.openAIProperties.getKey()))
      .endpoint(this.openAIProperties.getEndpoint())
      .buildAsyncClient();
  }

  @Bean
  public ChatCompletionService completionService(OpenAIAsyncClient client) {
    return OpenAIChatCompletion.builder()
      .withModelId(this.openAIProperties.getDeploymentName())
      .withOpenAIAsyncClient(client)
      .build();
  }

  @Bean
  public Kernel kernel(ChatCompletionService completionService) {
    return Kernel.builder()
      .withAIService(ChatCompletionService.class, completionService)
      .build();
  }

  @Bean
  public PromptExecutionSettings promptExecutionSettings() {
    return PromptExecutionSettings.builder()
      .withMaxTokens(1024)
      .withTemperature(0.5)
      .build();
  }

  @Bean
  public InvocationContext invocationContext(PromptExecutionSettings executionSettings) {
    return new InvocationContext.Builder()
      .withReturnMode(InvocationReturnMode.LAST_MESSAGE_ONLY)
      .withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
      .withPromptExecutionSettings(executionSettings)
      .build();
  }
}
