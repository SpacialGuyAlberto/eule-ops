package com.euleops.notificationsvc.service;

import com.euleops.notificationsvc.config.MailProperties;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendGridEmailSender implements EmailSender {

    private final SendGrid sendGrid;
    private final MailProperties props;

    @Override
    public void send(String to, String subject, String htmlBody) throws Exception {
        Mail mail = new Mail(
                new Email(props.getFrom()),
                subject,
                new Email(to),
                new Content("text/html", htmlBody)
        );

        SendGrid sg = new SendGrid("REMOVED");

        Request req = new Request();
        req.setMethod(Method.POST);
        req.setEndpoint("mail/send");
        req.setBody(mail.build());

        Response response = sg.api(req);
        System.out.println("Response Code: " + response.getStatusCode());
    }
}
