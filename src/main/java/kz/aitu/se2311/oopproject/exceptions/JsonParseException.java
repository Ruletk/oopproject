package kz.aitu.se2311.oopproject.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class JsonParseException extends RuntimeException {
    public JsonParseException(List<ObjectError> errors) {
        super(JsonParseException.getStringByListErrors(errors));
    }

    public static String getStringByListErrors(List<ObjectError> errors) {
        return "Invalid JSON structure";
    }
}
