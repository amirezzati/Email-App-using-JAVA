import java.io.Serializable;

public class Mail implements Serializable {

    private String time;
    private String subject;
    private String sender;
    private String receivers;
    private String text;

    public Mail(String time, String subject, String sender, String receivers, String text) {
        this.time = time;
        this.subject = subject;
        this.sender = sender;
        this.receivers = receivers;
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public String getReceivers() {
        return receivers;
    }

    public String getText() {
        return text;
    }
}
