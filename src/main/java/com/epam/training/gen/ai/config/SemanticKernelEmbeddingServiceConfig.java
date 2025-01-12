package com.epam.training.gen.ai.config;

import ai.djl.Model;
import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.shared.ModelDeployment;
import com.microsoft.semantickernel.aiservices.openai.textembedding.OpenAITextEmbeddingGenerationService;
import com.microsoft.semantickernel.services.textembedding.TextEmbeddingGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelEmbeddingServiceConfig {

  private final OpenAIAsyncClient client;

  @Autowired
  public SemanticKernelEmbeddingServiceConfig(OpenAIAsyncClient client) {
    this.client = client;
  }

  @Bean
  public TextEmbeddingGenerationService textEmbeddingGenerationService() {
    return new OpenAITextEmbeddingGenerationService.Builder()
      .withOpenAIAsyncClient(this.client)
      .withModelId(ModelDeployment.TEXT_EMBEDDING_ADA_002.getDeploymentId())
      .withDeploymentName(ModelDeployment.TEXT_EMBEDDING_ADA_002.getDeploymentId())
      .build();
  }
}
