package es.unizar.murcy.model;

import es.unizar.murcy.exception.TemplateBadRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class TemplateRequest {

    @Getter
    @Setter
    String htmlBody;

    @Getter
    @Setter
    String css;

    @Getter
    @Setter
    String subject;

    @Getter
    @Setter
    String templateName;

    public Template transformToEntity() {
        Template template = new Template();

        templatePolicies();

        template.setRequiredArguments(formatAndGetFormattedKeys());
        template.setHtmlTemplate(htmlBody);
        template.setTemplateName(templateName);
        template.setCss(css == null ? "" : css);
        template.setSubject(subject);

        return template;
    }

    private void templatePolicies() {
        if(!htmlBody.contains("${CSS}")) {
            throw new TemplateBadRequestException("CSS");
        } else if (templateName == null) {
            throw new TemplateBadRequestException("templateName");
        }
    }

    private Set<String> getKeysUnFormatted() {
        Pattern pattern = Pattern.compile("\\$\\{(?<argument>\\S*)\\}");
        Set<String> arguments = new HashSet<>();

        Matcher matcher = pattern.matcher(htmlBody);
        while (matcher.find()) {
            String argument = matcher.group("argument");
            arguments.add(argument);
        }

        matcher = pattern.matcher(subject);
        while (matcher.find()) {
            String argument = matcher.group("argument");
            arguments.add(argument);
        }

        return arguments;
    }

    private Set<String> formatAndGetFormattedKeys() {
        Set<String> argumentsUnFormatted = getKeysUnFormatted();
        Set<String> formattedArguments = new HashSet<>();
        for(String argument : argumentsUnFormatted) {
            String upperCaseArgument = argument.toUpperCase();
            if(!upperCaseArgument.equals("CSS") && !upperCaseArgument.equals("SUBJECT")) {
                if (Template.WHITE_ARGUMENTS.get(upperCaseArgument) == null) {
                    htmlBody = htmlBody.replace(argument, upperCaseArgument);
                    subject = subject.replace(argument, upperCaseArgument);
                    formattedArguments.add(argument.toUpperCase());
                } else {
                    String[] data = Template.WHITE_ARGUMENTS.get(upperCaseArgument);
                    upperCaseArgument = String.join("", data);
                    htmlBody = htmlBody.replace("${".concat(argument).concat("}"), upperCaseArgument);
                    subject = subject.replace(argument, upperCaseArgument);
                    formattedArguments.add(data[0].replace("${", "").replace("}", ""));
                }
            }
        }
        return formattedArguments;
    }
}
