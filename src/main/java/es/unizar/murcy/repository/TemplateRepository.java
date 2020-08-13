package es.unizar.murcy.repository;

import es.unizar.murcy.model.Template;
import es.unizar.murcy.repository.projection.SimplifiedTemplateProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findTemplateByTemplateName(String templateName);

    List<SimplifiedTemplateProjection> findAllProjectedBy();
}
