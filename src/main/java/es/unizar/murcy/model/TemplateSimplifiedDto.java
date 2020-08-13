package es.unizar.murcy.model;

import es.unizar.murcy.repository.projection.SimplifiedTemplateProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class TemplateSimplifiedDto {

    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    String templateName;

    public TemplateSimplifiedDto (Template template) {
        this.id = template.getId();
        this.templateName = template.getTemplateName();
    }

    public TemplateSimplifiedDto (SimplifiedTemplateProjection template) {
        this.id = template.getId();
        this.templateName = template.getTemplateName();
    }
}
