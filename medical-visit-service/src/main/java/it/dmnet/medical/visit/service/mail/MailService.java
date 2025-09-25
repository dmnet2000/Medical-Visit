package it.dmnet.medical.visit.service.mail;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.mailer.Mailer;
import io.quarkus.mailer.Mail;

@ApplicationScoped
public class MailService {

    @Inject
    Mailer mailer;

    public void sendEmail(String to, String subject, String text) {
        mailer.send(
                Mail.withText(to, subject, text)
        );
    }
}