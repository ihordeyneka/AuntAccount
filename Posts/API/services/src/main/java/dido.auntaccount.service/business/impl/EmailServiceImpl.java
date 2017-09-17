package dido.auntaccount.service.business.impl;

import dido.auntaccount.dido.auntaccount.utils.PropertiesHandler;
import dido.auntaccount.service.business.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

public class EmailServiceImpl implements EmailService {

    private static final String FROM_EMAIL = PropertiesHandler.getProperty("email.from");
    private static final String EMAIL_PASSWORD = PropertiesHandler.getProperty("email.password");
    private static final String REGISTRATION_SUBJECT = "Complete Registration With DIDO";
    private static final String REGISTRATION_MESSAGE = "Follow link to complete your registration ";
    private static final String ACTIVATION_URL = "http://localhost:8080/api/service/users/activate?token=";

    public void sendCompleteRegistration(String to, String token) {
        send(to, REGISTRATION_SUBJECT, REGISTRATION_MESSAGE + ACTIVATION_URL + token);
    }

    public void send(String to, String sub, String msg) {
        //Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(FROM_EMAIL, EMAIL_PASSWORD);
                    }
                });
        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(sub);
            message.setText(msg);
            //send message
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}




