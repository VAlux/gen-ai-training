package com.epam.training.gen.ai.document.reader;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Optional;

public enum ContentType {
  TXT(MediaType.TEXT_PLAIN_VALUE),
  PDF(MediaType.APPLICATION_PDF_VALUE);

  private final String mediaType;

  ContentType(String mediaType) {
    this.mediaType = mediaType;
  }

  public static Optional<ContentType> infer(String type) {
    if (StringUtils.isNotBlank(type)) {
      return Arrays.stream(values()).filter(value -> value.mediaType.equalsIgnoreCase(type)).findFirst();
    }

    return Optional.empty();
  }
}
