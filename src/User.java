import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    ArrayList<Mail> inboxMail = new ArrayList<Mail>();
    ArrayList<Mail> sentMail = new ArrayList<Mail>();

    public User (String firstName, String lastName, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addToInboxMail(Mail m) {
        inboxMail.add(m);
    }

    public void addToSentMail(Mail m) {
        sentMail.add(m);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return getFirstName()+" "+getLastName();
    }

    public ArrayList<Mail> getInboxMail() {
        return inboxMail;
    }

    public ArrayList<Mail> getSentMail() {
        return sentMail;
    }
}
