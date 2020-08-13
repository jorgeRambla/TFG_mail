package es.unizar.murcy.exception;

import lombok.Getter;

public class TemplateBadRequestException extends RuntimeException {

    @Getter
    private final String argumentName;

    public TemplateBadRequestException(String argumentName) {
        super();
        this.argumentName = argumentName;
    }

}
