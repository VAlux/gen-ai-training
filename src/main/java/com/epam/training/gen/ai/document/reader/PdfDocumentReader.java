package com.epam.training.gen.ai.document.reader;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Component
public class PdfDocumentReader implements DocumentReader {

  private final PDFTextStripper pdfStripper;

  public PdfDocumentReader() {
    this.pdfStripper = new PDFTextStripper();
  }

  @Override
  public Optional<Document> read(DocumentContainer<?> source) {
    try (InputStream contentStream = (InputStream) source.content;
         PDDocument pdfDocument = Loader.loadPDF(contentStream.readAllBytes())) {
      return Optional.of(new Document(this.pdfStripper.getText(pdfDocument)));
    } catch (IOException e) {
      throw new RuntimeException("Failed to read PDF content", e);
    }
  }

  @Override
  public ContentType getContentType() {
    return ContentType.PDF;
  }
}
