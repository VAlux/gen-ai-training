package com.epam.training.gen.ai.embedding;

import com.epam.training.gen.ai.document.reader.ContentType;
import com.epam.training.gen.ai.document.reader.DocumentReader.DocumentContainer;
import com.epam.training.gen.ai.document.reader.DocumentReaderRegistry;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class VectorStorageService {

  private final VectorStore vectorStore;
  private final DocumentReaderRegistry readerRegistry;
  private final TokenTextSplitter textSplitter;

  @Autowired
  public VectorStorageService(VectorStore vectorStore,
                              DocumentReaderRegistry readerRegistry,
                              TokenTextSplitter textSplitter) {
    this.vectorStore = vectorStore;
    this.readerRegistry = readerRegistry;
    this.textSplitter = textSplitter;
  }

  public List<Document> search(String query, Integer topK) {
    final var searchRequest = SearchRequest.builder().query(query).topK(topK).build();
    return this.vectorStore.similaritySearch(searchRequest);
  }

  public void persistDocument(Document document) {
    this.vectorStore.add(List.of(document));
  }

  public void persistDocument(InputStream stream, ContentType type) {
    this.readerRegistry.getReader(type)
      .flatMap(reader -> DocumentContainer.fromInputStream(stream, type).flatMap(reader::read))
      .map(this.textSplitter::split)
      .ifPresent(this.vectorStore::add);
  }

  public void persistDocument(DocumentContainer<?> container) {
    this.readerRegistry.getReader(container.getContentType())
      .ifPresent(reader -> reader.read(container));
  }

  public void persistDocuments(List<Document> documents) {
    this.vectorStore.add(documents);
  }
}