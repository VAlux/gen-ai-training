package com.epam.training.gen.ai.embedding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class VectorStorageInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(VectorStorageInitializer.class);

  private final VectorStorageService vectorStorageService;
  private final TokenTextSplitter textSplitter;

  @Value("classpath:/documents/cats.pdf")
  Resource resource;

  @Autowired
  public VectorStorageInitializer(VectorStorageService vectorStorageService) {
    this.vectorStorageService = vectorStorageService;

    // NO, we do not need to extract these magic numbers to the configuration, or somewhere else. This is just a PoC.
    this.textSplitter = new TokenTextSplitter(300, 300, 5, 1000, true);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    var reader = new TikaDocumentReader(this.resource);
    var content = reader.get();
    var splitDocuments = this.textSplitter.split(content);

    LOGGER.info("Embedding {} document chunks, please wait before performing the search...", splitDocuments.size());
    this.vectorStorageService.persistDocuments(splitDocuments);
    LOGGER.info("Embedded {} document chunks, you may proceed with searching", splitDocuments.size());
  }
}
