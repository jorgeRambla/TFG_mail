package es.unizar.murcy.service;

import es.unizar.murcy.model.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MailService {

    private final JavaMailSender emailSender;

    private final Logger logger = LoggerFactory.getLogger(MailService.class);

    public MailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMail(Template template, Map<String, String> arguments, String toEmail) {
        var mail = new Object() {
            public String html = template.getHtmlTemplate();
            public String subject = template.getSubject();
        };

        arguments
                .forEach((key, value) -> mail.html = mail.html
                        .replace("${prop}".replace("prop", key), value));

        arguments
                .forEach((key, value) -> mail.subject = mail.subject
                        .replace("${prop}".replace("prop", key), value));

        mail.html = mail.html.replace("${CSS}", template.getCss());

        MimeMessagePreparator preparation = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(toEmail);
            message.setSubject(mail.subject);
            message.setText(mail.html, true);
        };

        try {
            emailSender.send(preparation);
            logger.debug("Email send with template {}", template.getTemplateName());
        } catch (MailException me) {
            logger.error("Cannot send mail template {}, with exception error: {}", template.getTemplateName(), me.getMessage());
        }
    }

}
