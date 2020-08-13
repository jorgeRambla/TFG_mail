package es.unizar.murcy.service;

import es.unizar.murcy.model.Template;
import es.unizar.murcy.model.TemplateSimplifiedDto;
import es.unizar.murcy.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TemplateService {

    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Optional<Template> getTemplate(String templateName) {
        return templateRepository.findTemplateByTemplateName(templateName);
    }

    public Optional<Template> getTemplate(long id) {
        return templateRepository.findById(id);
    }

    public List<TemplateSimplifiedDto> getAllSimplified() {
        return templateRepository.findAllProjectedBy()
                .stream()
                .map(TemplateSimplifiedDto::new)
                .collect(Collectors.toList());
    }

    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }
    public Template update(Template template) {
        return templateRepository.save(template);
    }

}
