package com.epam.training.gen.ai.config;

import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SplitterConfig {

  @Bean
  public TokenTextSplitter splitter() {
    // NO, we do not need to extract these magic numbers to the configuration, or somewhere else. This is just a PoC.
    return new TokenTextSplitter(300, 300, 5, 1000, true);
  }
}
