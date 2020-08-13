package es.unizar.murcy.exception;

import lombok.Getter;

import java.util.Collection;

public class MailBadRequestArgumentsException extends RuntimeException {
    @Getter
    private final String argumentName;

    public MailBadRequestArgumentsException(String argumentName) {
        super();
        this.argumentName = argumentName;
    }

    public MailBadRequestArgumentsException(Collection<String> argumentList) {
        super();
        this.argumentName = String.join(",", argumentList);
    }
}
