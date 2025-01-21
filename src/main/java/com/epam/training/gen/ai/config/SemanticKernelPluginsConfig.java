package com.epam.training.gen.ai.config;

import com.epam.training.gen.ai.plugins.LightsPlugin;
import com.epam.training.gen.ai.plugins.TimePlugin;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SemanticKernelPluginsConfig {

  @Bean(name = "time-plugin")
  public KernelPlugin timePlugin() {
    return KernelPluginFactory.createFromObject(new TimePlugin(), "TimePlugin");
  }

  @Bean(name = "lights-plugin")
  public KernelPlugin lightsPlugin() {
    return KernelPluginFactory.createFromObject(new LightsPlugin(), "LightsPlugin");
  }
}
