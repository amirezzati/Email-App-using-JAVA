import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) {


        try  {
            Socket socket = new Socket("127.0.0.1", 1221);

            try { // Look and Feel
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (Exception useDefault) {}

            MailFrame mailFrame = new MailFrame(socket);


        } catch (UnknownHostException ex) {
            ex.getStackTrace();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}

