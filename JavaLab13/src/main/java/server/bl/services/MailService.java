package server.bl.services;

public interface MailService {
    void send(String subject, String fromEmail, String toEmail, String... data);
}
