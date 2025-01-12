package com.epam.training.gen.ai.embedding;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorStorageService {

  private final VectorStore vectorStore;

  @Autowired
  public VectorStorageService(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  public List<Document> search(String query, Integer topK) {
    final var searchRequest = SearchRequest.builder().query(query).topK(topK).build();
    return this.vectorStore.similaritySearch(searchRequest);
  }

  public void persistDocument(Document document) {
    this.vectorStore.add(List.of(document));
  }

  public void persistDocuments(List<Document> documents) {
    this.vectorStore.add(documents);
  }
}