package es.unizar.murcy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@NoArgsConstructor
public class MailRequest {

    @Getter
    @Setter
    String toEmail;

    @Getter
    @Setter
    String templateName;

    @Getter
    @Setter
    Map<String, String> arguments;
}
