package com.epam.training.gen.ai.properties;

import com.epam.training.gen.ai.shared.ModelDeployment;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client-openai")
public class OpenAIConfigurationProperties {

  private String key;
  private String endpoint;
  private ModelDeployment modelDeployment;

  public String getKey() {
    return this.key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getEndpoint() {
    return this.endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public ModelDeployment getModelDeployment() {
    return this.modelDeployment;
  }

  public void setModelDeployment(ModelDeployment modelDeployment) {
    this.modelDeployment = modelDeployment;
  }
}