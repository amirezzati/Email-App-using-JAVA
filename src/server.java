import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class server {

    public static void main(String[] args) {

        ArrayList<User> users = new ArrayList<>();

        // first, write arraylist object from file
        try {
            FileInputStream inputStream = new FileInputStream("save.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            System.out.println("Read Save File");
            users = (ArrayList<User>) objectInputStream.readObject();
            System.out.println("Write User's ArrayList From File To ArrayList Object");
            objectInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.getStackTrace();
        }
        int port = 1221;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                System.out.println("socket is ready to connect");
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                MyThread thread = new MyThread(socket, users);
                thread.start();

                // write array list to file
                try {
                    FileOutputStream outputStream = new FileOutputStream("save.txt");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                    objectOutputStream.writeObject(users);

                    objectOutputStream.close();
                    outputStream.close();
                } catch (IOException e) {
                    e.getStackTrace();
                }
            }

        } catch (IOException e) {
            e.getStackTrace();
        }

    }


    // when client connect to server, create new thread for him.
    static class MyThread extends Thread {
        Socket socket;
        ArrayList<User> users;

        public MyThread(Socket socket, ArrayList<User> users) {
            this.socket = socket;
            this.users = users;
        }

        public void run() {

            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                // received message
                while (true) {
                    String data = in.readUTF();

                    if (data.startsWith("Login**")) { // Log In User

                        String[] login = data.substring(7).split("&", 2);

                        if (checkLoginData(login[0], login[1], users)) {
                            // if user find out in array list so write it name's to Email frame
                            out.writeUTF("User&Name**" + findUserByUsername(login[0], users).getUsername()
                                    + "&" + findUserByUsername(login[0], users).getFullName());
                        } else {
                            out.writeUTF("UserNotFound");
                        }

                    } else if (data.startsWith("SignUp**")) { // Sign Up User
                        String SignUp = data.substring(8);

                        out.writeBoolean(checkSignUpData(SignUp, users)); // if username is available write true

                        if (checkSignUpData(SignUp, users)) {
                            data = in.readUTF();
                            // data = "SignUpData** firstName & lastName & username & password"
                            String[] SignUpData = data.substring(12).split("&", 4);
                            User newUser = new User(SignUpData[0], SignUpData[1], SignUpData[2], SignUpData[3]);
                            users.add(newUser); // add user to arrayList
                        }

                    } else if (data.startsWith("InboxMail**")) { // show inbox mails
                        String username = data.substring(11);
                        User u = findUserByUsername(username, users);

                        ArrayList<Mail> inboxMails = u.getInboxMail();
                        out.writeInt(inboxMails.size());
                        for (int i = inboxMails.size() - 1; i >= 0; i--) {
                            out.writeUTF(inboxMails.get(i).getTime()); // write mails time
                            out.writeUTF(inboxMails.get(i).getSubject()); // write mails subject
                            out.writeUTF(inboxMails.get(i).getSender()); // write mails sender
                            out.writeUTF(inboxMails.get(i).getReceivers()); // write mails receivers
                            out.writeUTF(inboxMails.get(i).getText()); // write mails text
                        }
                    } else if (data.startsWith("SentMail**")) {
                        String username = data.substring(10);
                        User u = findUserByUsername(username, users);

                        ArrayList<Mail> sentMails = u.getSentMail();
                        out.writeInt(sentMails.size()); // write size of sentMails ArrayList
                        for (int i = sentMails.size() - 1; i >= 0; i--) {
                            out.writeUTF(sentMails.get(i).getTime()); // write mails time
                            out.writeUTF(sentMails.get(i).getSubject()); // write mails subject
                            out.writeUTF(sentMails.get(i).getSender()); // write mails sender
                            out.writeUTF(sentMails.get(i).getReceivers()); // write mails receivers
                            out.writeUTF(sentMails.get(i).getText()); // write mails text
                        }
                    } else if (data.startsWith("SendMail**")) { // sent mail
                        String[] inputs = data.substring(10).split("&", 4);

                        String[] usernames = inputs[1].split(",");
                        // input[0] = sender username,  input[1] = receivers username
                        // input[2] = Mail's Subject,  input[3] = Mail's Text

                        // create new date and convert to my needs form
                        Date t = new Date();
                        String pattern = "yyyy-MM-dd  HH:mm:ss"; // show pattern
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        Mail m = new Mail(simpleDateFormat.format(t), inputs[2], inputs[0],
                                inputs[1], inputs[3]);

                        boolean firstTime = true;
                        String invalidUsername = "";
                        for (String x : usernames) {
                            if (findUserByUsername(x, users) != null) {
                                findUserByUsername(x, users).addToInboxMail(m);
                                // if exist receivers username
                                if (firstTime) { // for first time add mail to sender's sent mail
                                    findUserByUsername(inputs[0], users).addToSentMail(m);
                                    firstTime = false;
                                }
                            } else { // if username not found in array list
                                invalidUsername += "," + x;
                            }
                        }
                        out.writeUTF("SomeUsernameNotFound**" + invalidUsername);

                    } else if (data.startsWith("Setting**")) { // show user information when click on setting
                        String username = data.substring(9);
                        User u = findUserByUsername(username, users);
                        // show this data in setting panel
                        out.writeUTF(u.getUsername() + "&" + u.getPassword()
                                + "&" + u.getFirstName() + "&" + u.getLastName());

                    } else if (data.startsWith("ChangeData**")) {
                        String username = data.substring(12);
                        User u = findUserByUsername(username, users);

                        String newUserName = in.readUTF();

                        // check new username is available or not?
                        out.writeBoolean(checkSignUpData(newUserName, users));

                        String[] information = in.readUTF().split("&", 4);
                        if (checkSignUpData(newUserName, users) || username.equals(information[0])) {
                            // set new data for user
                            u.setUsername(information[0]);
                            u.setPassword(information[1]);
                            u.setFirstName(information[2]);
                            u.setLastName(information[3]);
                        }
                    }

                }
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
    }

    static public boolean checkLoginData(String username, String password, ArrayList<User> user) {
        for (User x: user) {
            if (x.getUsername().equals(username)) {
                if (x.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    static public boolean checkSignUpData(String username, ArrayList<User> user) {
        for (User x: user) {
            if (x.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    static public User findUserByUsername(String username, ArrayList<User> user) {
        for (User x: user) {
            if (x.getUsername().equals(username)) {
                return x;
            }
        }
        return null;
    }

}
