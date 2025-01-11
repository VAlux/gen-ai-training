package com.epam.training.gen.ai.plugins;

import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.util.StringUtils.parseLocale;

public class TimePlugin {

  private static final Logger LOGGER = LoggerFactory.getLogger(TimePlugin.class);

  private static final String DAY_MONTH_DAY_YEAR = "EEEE, MMMM d, yyyy";

  /**
   * Get the current date and time for the system default timezone.
   *
   * @return a ZonedDateTime object with the current date and time.
   */
  public ZonedDateTime now() {
    return ZonedDateTime.now(ZoneId.systemDefault());
  }

  /**
   * Get the current date.
   *
   * <p>Example: {{time.date}} => Sunday, January 12, 2025
   *
   * @return The current date.
   */
  @DefineKernelFunction(name = "date", description = "Get the current date")
  public String date(
    @KernelFunctionParameter(
      name = "locale",
      description = "Locale to use when formatting the date",
      required = false) String locale) {
    LOGGER.info("Getting current data for locale: {}", locale);

    return DateTimeFormatter.ofPattern(DAY_MONTH_DAY_YEAR)
      .withLocale(parseLocale(locale))
      .format(now());
  }

  /**
   * Get the current time.
   *
   * <p>Example: {{time.time}} => 9:15:00 AM
   *
   * @return The current time.
   */
  @DefineKernelFunction(name = "time", description = "Get the current time")
  public String time(
    @KernelFunctionParameter(
      name = "locale",
      description = "Locale to use when formatting the date",
      required = false)
    String locale) {
    LOGGER.info("Getting current time for locale: {}", locale);

    return DateTimeFormatter.ofPattern("hh:mm:ss a")
      .withLocale(parseLocale(locale))
      .format(now());
  }
}
