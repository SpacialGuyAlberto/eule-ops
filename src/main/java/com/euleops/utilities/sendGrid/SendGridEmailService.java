package com.euleops.utilities.sendGrid;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridEmailService {
    @Value("${spring.sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${DOMAIN_ORIGIN_URL}")
    private String domainUrl;

    public void sendActivationEmail(String to, String token) {
        Email from = new Email("info@astralisbank.com"); // Cambia esto a tu email
        Email toEmail = new Email(to);
        String subject = "Email de activación";
        String activationLink = domainUrl + "/activate?token=" + token;
        String body = "Haga clic en el siguiente enlace para activar su cuenta: " + activationLink;
        Content content = new Content("text/html", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());
            System.out.println("Response Headers: " + response.getHeaders());
        } catch (IOException ex) {
            System.err.println("Failed to send email to: " + to);
            ex.printStackTrace();
        }
    }
}
