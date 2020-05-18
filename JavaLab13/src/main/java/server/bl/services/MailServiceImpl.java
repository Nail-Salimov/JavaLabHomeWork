package server.bl.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import server.addition.email.EmailCreator;
import server.configs.MailConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class MailServiceImpl implements MailService{

    @Value("${db.mail}")
    private String username;
    @Value("${db.passwordMail}")
    private String password;
    private Properties props;

    @Autowired
    private EmailCreator emailCreator;

    @Autowired
    private MailConfig config;

    public void send(String subject, String fromEmail, String toEmail, String... data) {
        props = config.getProperties();

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            //от кого
            message.setFrom(new InternetAddress(username));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            //Заголовок письма
            message.setSubject(subject);
            //Содержимое

            String text = emailCreator.createEmail(data);
            message.setText(text);

            message.setContent(text, "text/html");


            //Отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }
}
