package com.epam.training.gen.ai.document.reader;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TxtDocumentReader implements DocumentReader {

  @Override
  public Optional<Document> read(DocumentContainer<?> source) {
    return Optional.of(new Document((String) source.content));
  }

  @Override
  public ContentType getContentType() {
    return ContentType.TXT;
  }
}
