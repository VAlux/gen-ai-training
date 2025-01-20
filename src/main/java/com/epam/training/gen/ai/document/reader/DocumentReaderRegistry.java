package com.epam.training.gen.ai.document.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DocumentReaderRegistry {

  private final Map<ContentType, DocumentReader> registry;
  private final Map<ContentType, List<DocumentReader>> availableReaders;

  @Autowired
  public DocumentReaderRegistry(List<DocumentReader> documentReaders) {
    this.availableReaders = documentReaders.stream().collect(Collectors.groupingBy(DocumentReader::getContentType));
    this.registry = createRegistry();
  }

  private Map<ContentType, DocumentReader> createRegistry() {
    final Map<ContentType, DocumentReader> registry = HashMap.newHashMap(ContentType.values().length);

    Arrays.stream(ContentType.values())
      .forEach(type -> locateReader(type).ifPresent(reader -> registry.put(type, reader)));

    return registry;
  }

  private Optional<DocumentReader> locateReader(ContentType contentType) {
    return this.availableReaders.get(contentType).stream().findFirst();
  }

  public Optional<DocumentReader> getReader(ContentType type) {
    return Optional.ofNullable(this.registry.get(type));
  }
}
