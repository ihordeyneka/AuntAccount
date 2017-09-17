package dido.auntaccount.service.business;

public interface EmailService {

    void send(String to, String sub, String msg);

    void sendCompleteRegistration(String to, String token);
}
