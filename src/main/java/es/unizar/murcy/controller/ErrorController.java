package es.unizar.murcy.controller;

import es.unizar.murcy.exception.MailBadRequestArgumentsException;
import es.unizar.murcy.exception.TemplateBadRequestException;
import es.unizar.murcy.exception.TemplateNotFoundException;
import es.unizar.murcy.exception.TemplateTemplateNameBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = MailException.class)
    public ResponseEntity<Object> exception(MailException me) {
        return new ResponseEntity<>("Mail service is unreachable", HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = TemplateNotFoundException.class)
    public ResponseEntity<Object> exception(TemplateNotFoundException tnfe) {
        return new ResponseEntity<>("Template not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MailBadRequestArgumentsException.class)
    public ResponseEntity<Object> exception(MailBadRequestArgumentsException mbrae) {
        return new ResponseEntity<>("These arguments are required: {}".replace("{}", mbrae.getArgumentName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TemplateBadRequestException.class)
    public ResponseEntity<Object> exception(TemplateBadRequestException tbre) {
        return new ResponseEntity<>("Argument {} is required".replace("{}", tbre.getArgumentName()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TemplateTemplateNameBadRequestException.class)
    public ResponseEntity<Object> exception(TemplateTemplateNameBadRequestException ttnbre) {
        return new ResponseEntity<>("templateName must be unique", HttpStatus.BAD_REQUEST);
    }

}