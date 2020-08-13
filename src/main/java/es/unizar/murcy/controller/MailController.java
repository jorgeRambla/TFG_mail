package es.unizar.murcy.controller;

import es.unizar.murcy.exception.TemplateBadRequestException;
import es.unizar.murcy.exception.TemplateNotFoundException;
import es.unizar.murcy.exception.TemplateTemplateNameBadRequestException;
import es.unizar.murcy.model.MailRequest;
import es.unizar.murcy.model.Template;
import es.unizar.murcy.model.TemplateRequest;
import es.unizar.murcy.model.TemplateSimplifiedDto;
import es.unizar.murcy.service.MailService;
import es.unizar.murcy.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MailController {

    private final TemplateService templateService;
    private final MailService mailService;

    public MailController(TemplateService templateService, MailService mailService) {
        this.templateService = templateService;
        this.mailService = mailService;
    }

    @PostMapping("/mail/send")
    public ResponseEntity<String> sendMail(@RequestBody MailRequest mailRequest) {
        Template template = templateService.getTemplate(mailRequest.getTemplateName())
                .orElseThrow(TemplateNotFoundException::new);

        template.validateArguments(mailRequest.getArguments());

        mailService.sendMail(template, mailRequest.getArguments(), mailRequest.getToEmail());

        return ResponseEntity.status(HttpStatus.OK).body("Mail send");
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<Template> getTemplateById(@PathVariable long id) {
        Template template = templateService.getTemplate(id)
                .orElseThrow(TemplateNotFoundException::new);

        return ResponseEntity.status(HttpStatus.OK).body(template);

    }

    @GetMapping("/template/list")
    public ResponseEntity<List<TemplateSimplifiedDto>> getTemplateList() {
        return ResponseEntity.status(HttpStatus.OK).body(templateService.getAllSimplified());
    }

    @PutMapping("/template")
    public ResponseEntity<Template> updateTemplate() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/template")
    public ResponseEntity<TemplateSimplifiedDto> createTemplate(@RequestBody TemplateRequest templateRequest) {
        Optional<Template> templateOptional = templateService.getTemplate(templateRequest.getTemplateName());
        if (templateOptional.isPresent()) {
            throw new TemplateTemplateNameBadRequestException();
        }

        Template template = templateRequest.transformToEntity();
        return ResponseEntity.status(HttpStatus.CREATED).body(new TemplateSimplifiedDto(templateService.createTemplate(template)));
    }
}
