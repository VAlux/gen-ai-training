package com.epam.training.gen.ai.config;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.epam.training.gen.ai.properties.OpenAIConfigurationProperties;
import com.epam.training.gen.ai.properties.PromptExecutionConfigurationProperties;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.InvocationReturnMode;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SemanticKernelConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(SemanticKernelConfig.class);

  private final OpenAIConfigurationProperties openAIProperties;
  private final PromptExecutionConfigurationProperties promptExecutionProperties;
  private final List<KernelPlugin> plugins;

  @Autowired
  public SemanticKernelConfig(OpenAIConfigurationProperties openAIProperties,
                              PromptExecutionConfigurationProperties promptExecutionProperties,
                              List<KernelPlugin> plugins) {
    this.openAIProperties = openAIProperties;
    this.promptExecutionProperties = promptExecutionProperties;
    this.plugins = plugins;
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
      .withModelId(this.openAIProperties.getModelDeployment().getDeploymentId())
      .withOpenAIAsyncClient(client)
      .build();
  }

  @Bean
  public Kernel kernel(ChatCompletionService completionService) {
    Kernel.Builder builder = Kernel.builder().withAIService(ChatCompletionService.class, completionService);
    this.plugins.forEach(builder::withPlugin);
    LOGGER.info("Added {} plugin(s) to the kernel", this.plugins.size());

    return builder.build();
  }

  @Bean
  public PromptExecutionSettings promptExecutionSettings() {
    return PromptExecutionSettings.builder()
      .withMaxTokens(this.promptExecutionProperties.getMaxTokens())
      .withTemperature(this.promptExecutionProperties.getTemperature())
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
