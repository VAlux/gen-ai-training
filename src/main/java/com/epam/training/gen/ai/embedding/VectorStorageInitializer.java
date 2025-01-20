package com.epam.training.gen.ai.embedding;

import com.epam.training.gen.ai.document.reader.ContentType;
import com.epam.training.gen.ai.document.reader.DocumentReader.DocumentContainer;
import com.epam.training.gen.ai.document.reader.PdfDocumentReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class VectorStorageInitializer {

  private static final Logger LOGGER = LoggerFactory.getLogger(VectorStorageInitializer.class);

  private final VectorStorageService vectorStorageService;
  private final TokenTextSplitter textSplitter;
  private final PdfDocumentReader pdfDocumentReader;

  @Value("classpath:/documents/cats.pdf")
  Resource resource;

  @Autowired
  public VectorStorageInitializer(VectorStorageService vectorStorageService,
                                  TokenTextSplitter textSplitter,
                                  PdfDocumentReader pdfDocumentReader) {
    this.vectorStorageService = vectorStorageService;
    this.textSplitter = textSplitter;
    this.pdfDocumentReader = pdfDocumentReader;
  }

  //  @EventListener(ApplicationReadyEvent.class)
  public void init() {
    DocumentContainer.fromResource(this.resource, ContentType.PDF)
      .flatMap(this.pdfDocumentReader::read)
      .map(this.textSplitter::split)
      .ifPresent(documents -> {
        LOGGER.info("Embedding {} document chunks, please wait before performing the search...", documents.size());
        this.vectorStorageService.persistDocuments(documents);
        LOGGER.info("Embedded {} document chunks, you may proceed with searching", documents.size());
      });
  }
}
