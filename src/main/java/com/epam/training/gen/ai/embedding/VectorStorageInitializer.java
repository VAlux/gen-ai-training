package com.epam.training.gen.ai.embedding;

import com.epam.training.gen.ai.document.reader.ContentType;
import com.epam.training.gen.ai.document.reader.DocumentReader;
import com.epam.training.gen.ai.document.reader.DocumentReader.DocumentContainer;
import com.epam.training.gen.ai.document.reader.PdfDocumentReader;
import com.epam.training.gen.ai.document.reader.TxtDocumentReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final PdfDocumentReader pdfDocumentReader;
  private final TxtDocumentReader txtDocumentReader;

  @Value("classpath:/documents/cats.pdf")
  Resource catsResource;

  @Value("classpath:/documents/monads.txt")
  Resource monadsResource;

  @Autowired
  public VectorStorageInitializer(VectorStorageService vectorStorageService,
                                  TokenTextSplitter textSplitter,
                                  PdfDocumentReader pdfDocumentReader,
                                  TxtDocumentReader txtDocumentReader) {
    this.vectorStorageService = vectorStorageService;
    this.textSplitter = textSplitter;
    this.pdfDocumentReader = pdfDocumentReader;
    this.txtDocumentReader = txtDocumentReader;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void init() {
//    embed(this.catsResource, ContentType.PDF, this.pdfDocumentReader);
    embed(this.monadsResource, ContentType.TXT, this.txtDocumentReader);
  }

  private void embed(Resource resource, ContentType contentType, DocumentReader documentReader) {
    DocumentContainer.fromResource(resource, contentType)
      .flatMap(documentReader::read)
      .map(this.textSplitter::split)
      .ifPresent(documents -> {
        LOGGER.info("Embedding {} document chunks, please wait before performing the search...", documents.size());
        this.vectorStorageService.persistDocuments(documents);
        LOGGER.info("Embedded {} document chunks, you may proceed with searching", documents.size());
      });
  }
}
