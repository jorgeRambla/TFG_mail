package es.unizar.murcy.model;

import es.unizar.murcy.exception.MailBadRequestArgumentsException;
import es.unizar.murcy.model.jpa.StringSetConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(name = "murcy_mail_template")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private long id;

    @Getter
    @Setter
    @Column(length=256, unique = true)
    private String templateName;

    @Lob
    @Getter
    @Setter
    private String htmlTemplate;

    @Lob
    @Getter
    @Setter
    private String css;

    @Getter
    @Setter
    @Convert(converter = StringSetConverter.class)
    private Set<String> requiredArguments;

    @Getter
    @Setter
    @Column(length=256)
    private String subject;

    public static final Map<String, String[]> WHITE_ARGUMENTS = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>("BLANK_GMAIL", new String[]{"${BACK_END_URL}", "/blank.gif"}),
            new AbstractMap.SimpleImmutableEntry<>("MURCY_ICON", new String[]{"${BACK_END_URL}", "/logotype.jpeg"}))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public void validateArguments(Map<String, String> incomingArguments) {
        List<String> errors = new ArrayList<>();
        requiredArguments.forEach(key -> {
            if(incomingArguments.get(key) == null) {
                errors.add(key);
            }
        });

        if (!errors.isEmpty()) {
            throw new MailBadRequestArgumentsException(errors);
        }
    }
}
