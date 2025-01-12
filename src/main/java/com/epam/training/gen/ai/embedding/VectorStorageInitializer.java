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

  @Value("classpath:/documents/cats.pdf")
  Resource alice;

  @Autowired
  public VectorStorageInitializer(VectorStorageService vectorStorageService) {
    this.vectorStorageService = vectorStorageService;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    TikaDocumentReader aliceReader = new TikaDocumentReader(this.alice);
    var aliceContent = aliceReader.get();
    var splitDocuments = new TokenTextSplitter(300, 300, 5, 1000, true).split(aliceContent);

    LOGGER.info("Embedding {} document chunks, please wait before performing the search...", splitDocuments.size());
    this.vectorStorageService.persistDocuments(splitDocuments);
    LOGGER.info("Embedded {} document chunks, you may proceed with searching", splitDocuments.size());
  }
}
