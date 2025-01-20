package com.epam.training.gen.ai.document.reader;

import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface DocumentReader {
  sealed abstract class DocumentContainer<S> {
    protected final S content;

    public DocumentContainer(S content) {
      this.content = content;
    }

    public abstract ContentType getContentType();

    public static final class TxtDocumentContainer extends DocumentContainer<String> {
      public TxtDocumentContainer(String content) {
        super(content);
      }

      @Override
      public ContentType getContentType() {
        return ContentType.TXT;
      }
    }

    public static final class PdfDocumentContainer extends DocumentContainer<InputStream> {
      public PdfDocumentContainer(InputStream content) {
        super(content);
      }

      @Override
      public ContentType getContentType() {
        return ContentType.PDF;
      }
    }

    public static Optional<DocumentContainer<?>> fromResource(Resource resource, ContentType contentType) {
      try {
        return fromInputStream(resource.getInputStream(), contentType);
      } catch (IOException e) {
        return Optional.empty();
      }
    }

    public static Optional<DocumentContainer<?>> fromInputStream(InputStream inputStream, ContentType contentType) {
      return switch (contentType) {
        case TXT -> {
          try {
            yield Optional.of(new TxtDocumentContainer(new String(inputStream.readAllBytes())));
          } catch (IOException e) {
            yield Optional.empty();
          }
        }
        case PDF -> Optional.of(new PdfDocumentContainer(inputStream));
      };
    }
  }

  Optional<Document> read(final DocumentContainer<?> source);

  ContentType getContentType();
}
