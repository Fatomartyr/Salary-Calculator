package ru.neoflex.salarycalculator.util;

import ru.neoflex.salarycalculator.exception.DateParseException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class DateParseUtil {

    public static final String DEFAULT_PATTERN = "dd.MM.yyyy";

    private static final List<String> SUPPORTED_PATTERNS = Arrays.asList(
            "yyyy-MM-dd",
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "yyyy/MM/dd"
    );

    private static final DateTimeFormatter DEFAULT_FORMATTER =
            DateTimeFormatter.ofPattern(DEFAULT_PATTERN);

    private static final List<DateTimeFormatter> SUPPORTED_FORMATTERS =
            SUPPORTED_PATTERNS.stream()
                    .map(DateTimeFormatter::ofPattern)
                    .collect(Collectors.toList());

    private DateParseUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new DateParseException("Date string cannot be null or empty");
        }
        try {
            return LocalDate.parse(dateStr.trim(), DEFAULT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new DateParseException(
                    String.format("Failed to parse date '%s' with pattern '%s'",
                            dateStr, DEFAULT_PATTERN), e);
        }
    }

    public static LocalDate parseDate(String dateStr, String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return parseDateWithFormatter(dateStr, formatter);
    }

    public static LocalDate parseDateWithFormatter(String dateStr, DateTimeFormatter formatter) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new DateParseException("Date string cannot be null or empty");
        }
        try {
            return LocalDate.parse(dateStr.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new DateParseException(
                    String.format("Failed to parse date '%s' with formatter pattern '%s'",
                            dateStr, formatter.toString()), e);
        }
    }

    public static LocalDate parseDateWithFallback(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new DateParseException("Date string cannot be null or empty");
        }

        String trimmed = dateStr.trim();

        for (DateTimeFormatter formatter : SUPPORTED_FORMATTERS) {
            try {
                return LocalDate.parse(trimmed, formatter);
            } catch (DateTimeParseException ignored) {}
        }

        throw new DateParseException(
                String.format("Failed to parse date '%s'. Supported formats: %s",
                        dateStr, String.join(", ", SUPPORTED_PATTERNS)));
    }

    public static String formatDate(LocalDate date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (pattern == null || pattern.trim().isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be null or empty");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }

}