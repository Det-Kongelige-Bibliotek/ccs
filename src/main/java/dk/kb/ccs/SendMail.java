package dk.kb.ccs;

/**
 * Created by nkh on 10-07-2018.
 */

import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class SendMail {
    private String to;
    private String from;
    private  String host;
    private  String subject;

    public SendMail(String to, String from, String host, String subject) {
        this.to = to;
        this.from = from;
        this.host = host;
        this.subject = subject;
    }

    public void sendmail() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText("This is my message");
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
