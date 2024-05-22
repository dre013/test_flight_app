package by.dre.je.jdbc.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@UtilityClass
public class LocalDateFormatter {
    private final static String PATTERN = "yyyy-MM-dd";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public boolean isValid(String date) {
        try {
            return Optional.ofNullable(date).map(LocalDateFormatter::format).isPresent();
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate format(String date) {
        return LocalDate.parse(date, FORMATTER);
    }
}
