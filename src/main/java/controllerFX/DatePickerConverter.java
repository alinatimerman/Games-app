package controllerFX;

import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DatePickerConverter extends StringConverter<LocalDate> {

    private String pattern = "dd-MM-yyyy";
    private final DateTimeFormatter dtFormatter;

    public DatePickerConverter() {
        dtFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DatePickerConverter(String pattern) {
        this.pattern = pattern;
        dtFormatter = DateTimeFormatter.ofPattern(pattern);
    }

    public DateTimeFormatter getDtFormatter(){
        return dtFormatter;
    }

    @Override
    public String toString(LocalDate date) {
        if (date != null) {
            return dtFormatter.format(date);
        }
        else
            return "";
    }

    @Override
    public LocalDate fromString(String text) {
        if (text != null && !text.isEmpty()) {
            return LocalDate.parse(text, dtFormatter);
        } else
            return null;
    }
}