import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MailFrame extends JFrame {

    ImageIcon emailIcon = new ImageIcon("img\\Email.png");
    String username;
    Socket socket;

    public MailFrame(Socket socket) { // Constructor can show Email Menu
        this.socket = socket;

        getContentPane().setBackground(new Color(0, 57, 94));
        setFocusable(false);
        setResizable(false);

        setSize(450,400);
        setLayout(null);

        setTitle("Email");
        setIconImage(emailIcon.getImage());

        JLabel logo = new JLabel(emailIcon);
        logo.setBounds(142,20 , 150, 150);
        add(logo);


        JButton logIn = new JButton("LOG IN");
        JButton signUp = new JButton("Sign Up");
        JButton exit = new JButton("Exit");

        logIn.setBounds(160,200,120,27);
        signUp.setBounds(160,240,120,27);
        exit.setBounds(160,280,120,27);
        logIn.setFocusPainted(false);
        signUp.setFocusPainted(false);
        exit.setFocusPainted(false);
        add(logIn);
        add(signUp);
        add(exit);

        logIn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                Login();
            }
        });

        signUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                SignUp();
            }
        });

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });








        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    } // Constructor can show Email Menu

    public void Login() { // Create Login Frame

        JFrame login = new JFrame("LOGIN");
        Icon loginLogo = new ImageIcon("img\\Login.png");
        ImageIcon loginIcon = new ImageIcon("img\\loginIcon.png");

        login.setIconImage(loginIcon.getImage());
        login.getContentPane().setBackground(new Color(0, 57, 94));
        login.setFocusable(false);
        login.setResizable(false);

        login.setSize(450,420);
        login.setLayout(null);

        JLabel logo = new JLabel(loginLogo);
        logo.setBounds(155,30 , 128, 128);
        login.add(logo);

        // Create label and textfield for username
        JLabel username = new JLabel("Username");
        JTextField userInput = new JTextField( 35);
        userInput.setBackground(null);

        login.add(username);
        login.add(userInput);
        username.setLabelFor(userInput);
        userInput.setBounds(130,200,180,25);
        userInput.setBackground(Color.WHITE);
        userInput.setForeground(new Color(0, 104, 75));
        username.setForeground(Color.WHITE);
        username.setBounds(195,180,70,20);

        // Create label and textfield for password
        JLabel password = new JLabel("Password");
        JPasswordField passInput = new JPasswordField( 35);
        passInput.setBackground(null);

        login.add(password);
        login.add(passInput);
        passInput.setBounds(130,250,180,25);
        passInput.setBackground(Color.WHITE);
        passInput.setForeground(new Color(0, 104, 75));
        password.setForeground(Color.WHITE);
        password.setBounds(195,230,70,20);

        // JButton for Login to Email Acc
        JButton loginButton = new JButton("LOG IN");
        loginButton.setFocusPainted(false);
        loginButton.setBounds(130,300,180,25);
        //login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Font f = loginButton.getFont();
        loginButton.setFont(f.deriveFont(f.getStyle() | Font.BOLD)); // Bold font for login button
        login.add(loginButton);

        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setForeground(new Color(0, 113, 6));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setForeground(null);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (userInput.getText().isEmpty() || String.valueOf(passInput.getPassword()).equals("")) {
                    JOptionPane.showMessageDialog(null,
                                                "Please Enter Your Username and Password!",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        /*ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());*/
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        // write input username and password to socket.
                        //out.flush();
                        out.writeUTF("Login**"+userInput.getText()+"&"+String.valueOf(passInput.getPassword()));
                        //out.flush();
                        // server checking login data and write false or true
                        String data = in.readUTF();

                        if (data.startsWith("User&Name**")) {
                            // if login data is exist, server write user's full name to socket.
                            userInput.setText("");
                            passInput.setText("");

                            String[] temp = data.substring(11).split("&",2);
                            setUsername(temp[0]);
                            Email(temp[1], login);

                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Your Username or Password is not correct",
                            "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });



        login.setVisible(true);
        login.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        login.setLocationRelativeTo(null);

        login.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
    } // Create Login Frame

    public void SignUp() { // Create Sign Up Frame
        JFrame signUp = new JFrame("Sign Up");
        Icon signUpLogo = new ImageIcon("img\\signup.png");
        ImageIcon signUpIcon = new ImageIcon("img\\signupIcon.png");

        signUp.setIconImage(signUpIcon.getImage());
        signUp.getContentPane().setBackground(new Color(0, 57, 94));
        signUp.setFocusable(false);
        signUp.setResizable(false);

        signUp.setSize(450,450);
        signUp.setLayout(null);

        JLabel logo = new JLabel(signUpLogo);
        logo.setBounds(160,40 , 128, 128);
        signUp.add(logo);

        JLabel[] labels = new JLabel[4];
        JTextField[] textFields = new JTextField[3];
        JPasswordField passwordField = new JPasswordField(30);
        passwordField.setBounds(170,290,150,20);
        passwordField.setForeground(new Color(0, 104, 75));
        signUp.add(passwordField);

        for (int i = 0; i<labels.length; i++) {
            labels[i] = new JLabel();
            signUp.add(labels[i]);
            labels[i].setForeground(Color.WHITE);
            labels[i].setBounds(110,200+30*i,70,20);
        }
        labels[0].setText("First Name");
        labels[1].setText("Last Name");
        labels[2].setText("Username");
        labels[3].setText("Password");

        for (int i = 0; i<textFields.length; i++) {
            textFields[i] = new JTextField();
            textFields[i].setColumns(30);
            textFields[i].setBackground(null);

            textFields[i].setBounds(170, 200+30*i, 150, 20);
            textFields[i].setBackground(Color.WHITE);
            textFields[i].setForeground(new Color(0, 104, 75));
            signUp.add(textFields[i]);
        }


        JButton cancel = new JButton("Cancel");
        JButton sign = new JButton("Sign Up");
        cancel.setBounds(110,330,70,20);
        sign.setBounds(190,330,130,20);
        //cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        //sign.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Font f = sign.getFont();
        sign.setFont(f.deriveFont(f.getStyle() | Font.BOLD)); // Bold font for login button
        signUp.add(cancel);
        signUp.add(sign);


        /*textFields[0].getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                //changed();
            }
            public void removeUpdate(DocumentEvent e) {
                changed();
            }
            public void insertUpdate(DocumentEvent e) {
                //changed();
            }

            public void changed() {
                if (textFields[0].getText().equals("")){
                    textFields[0].setText("amir");
                }
                else {
                    textFields[0].setEnabled(true);
                }

            }
        });*/
        sign.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sign.setForeground(new Color(0, 113, 6));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sign.setForeground(null);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // If one of text fields is empty, show error with message Dialog
                if (textFields[0].getText().isEmpty() || textFields[1].getText().isEmpty()
                        || textFields[2].getText().isEmpty() || String.valueOf(passwordField.getPassword()).equals("")) {

                    JOptionPane.showMessageDialog(null, "Please fill all Text fields", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        /*ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());*/
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        // write new username to server that check is available or not?
                        //out.flush();
                        out.writeUTF("SignUp**" + textFields[2].getText());
                        //out.flush();
                        if (in.readBoolean()) {
                            //out.flush();
                            out.writeUTF("SingUpData**" + textFields[0].getText() +
                                    "&" + textFields[1].getText() + "&" + textFields[2].getText()
                            + "&" + String.valueOf(passwordField.getPassword()));
                            //out.flush();
                            signUp.dispose();
                            setVisible(true);
                            JOptionPane.showMessageDialog(null,
                                    "Your Registration was Successful.",
                                    "Successfully",


                                    JOptionPane.PLAIN_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "This Username is not Available.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.getStackTrace();
                    }
                }
            }
        });

        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                signUp.dispose();
            }
        });

        /*JLabel password = new JLabel("Password");
        JTextField passInput = new JTextField( 35);
        passInput.setBackground(null);

        signUp.add(password);
        signUp.add(passInput);
        passInput.setBounds(130,310,180,25);
        passInput.setBackground(Color.WHITE);
        passInput.setForeground(new Color(0, 100, 125));
        password.setForeground(Color.WHITE);
        password.setBounds(190,280,70,20);*/







        signUp.setVisible(true);
        signUp.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        signUp.setLocationRelativeTo(null);

        signUp.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setVisible(true);
            }
        });
    } // Create Sign Up Frame

    public void Email(String fullName, JFrame login) { // Create Email Frame

        login.setVisible(false); // if email function is called, login frame will be closed.

        ImageIcon EmailIcon = new ImageIcon("img\\mail2.png");

        JFrame Email = new JFrame("Email");
        Email.setLayout(null);
        Email.setSize(800,500);
        Email.setLocationRelativeTo(null);
        Email.setIconImage(EmailIcon.getImage());

        // ------------------ Top Panel --------------------------
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setBounds(0,0,800,50);
        topPanel.setLayout(null);
        Email.add(topPanel);

        // LOGO Label
        ImageIcon logoIcon = new ImageIcon("img\\EmailFrame.png");
        JLabel logo = new JLabel(logoIcon); // Label for Show LOGO
        logo.setBounds(30,5,40,40);
        topPanel.add(logo);


        // Hi user label
        JLabel hiUser = new JLabel("Hi "+fullName); // Label for Show User's name
        hiUser.setForeground(Color.WHITE);
        hiUser.setBounds(130,0,200,50);
        hiUser.setFont(new Font(hiUser.getName(), Font.BOLD, hiUser.getFont().getSize()+7));
        topPanel.add(hiUser);


        // Log out label
        ImageIcon logOutIcon = new ImageIcon("img\\logOut.png");
        JLabel logOut = new JLabel(logOutIcon);
        logOut.setForeground(Color.WHITE);
        logOut.setBounds(730, 10, 30,30);
        topPanel.add(logOut);


        logOut.setCursor(new Cursor(HAND_CURSOR));
        logOut.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Email.dispose();
            }
        });


        // -------------------- Left Panel ----------------------------
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0, 57, 94));
        leftPanel.setBounds(0,0,120,500);
        leftPanel.setLayout(null);
        Email.add(leftPanel);


        // ------------------------ New mail button -----------------------------
        JPanel newMail = new JPanel();
        newMail.setBounds(0,100,120,40);
        newMail.setBackground(new Color(0, 57, 94));
        newMail.setLayout(null);
        newMail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(newMail);

        ImageIcon newMailIcon = new ImageIcon("img\\newMail.png");

        JLabel newMailLabel = new JLabel("New Mail"); // Label for New Mail Panel
        newMailLabel.setIcon(newMailIcon);
        newMailLabel.setForeground(Color.WHITE);
        newMailLabel.setBounds(15,0,120,35);
        newMailLabel.setFont(new Font(newMailLabel.getName(), Font.PLAIN, newMailLabel.getFont().getSize()+2));
        newMail.add(newMailLabel);

        // ------------------------ inbox mail button -----------------------------
        JPanel inbox = new JPanel();
        inbox.setBounds(0,160,120,40);
        inbox.setBackground(new Color(0, 57, 94));
        inbox.setLayout(null);
        inbox.setCursor(new Cursor(Cursor.HAND_CURSOR)); // add cursor for buttons
        leftPanel.add(inbox);

        ImageIcon inboxIcon = new ImageIcon("img\\inboxmail2.png");

        JLabel inboxLabel = new JLabel("Inbox Mail"); // Label for Inbox Panel
        inboxLabel.setIcon(inboxIcon);
        inboxLabel.setForeground(Color.WHITE);
        inboxLabel.setBounds(15,0,120,35);
        inboxLabel.setFont(new Font(inboxLabel.getName(), Font.PLAIN, inboxLabel.getFont().getSize()+2));
        inbox.add(inboxLabel);

        // --------------------------- sent mail button ---------------------------------
        JPanel sent = new JPanel();
        sent.setBounds(0,220,120,40);
        sent.setBackground(new Color(0, 57, 94));
        sent.setLayout(null);
        sent.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(sent);

        ImageIcon sentIcon = new ImageIcon("img\\sentmail2.png");

        JLabel sentLabel = new JLabel("Sent Mail"); // Label for Inbox Panel
        sentLabel.setIcon(sentIcon);
        sentLabel.setForeground(Color.WHITE);
        sentLabel.setBounds(15,0,120,37);
        sentLabel.setFont(new Font(sentLabel.getName(), Font.PLAIN, sentLabel.getFont().getSize()+2));
        sent.add(sentLabel);

        // ---------------------------- setting button -----------------------------
        JPanel setting = new JPanel();
        setting.setBounds(0,400,120,40);
        setting.setBackground(new Color(0, 57, 94));
        setting.setLayout(null);
        setting.setCursor(new Cursor(Cursor.HAND_CURSOR));
        leftPanel.add(setting);

        ImageIcon settingIcon = new ImageIcon("img\\setting.png");

        JLabel settingLabel = new JLabel("Setting"); // Label for Inbox Panel
        settingLabel.setIcon(settingIcon);
        settingLabel.setForeground(Color.WHITE);
        settingLabel.setBounds(23,0,120,37);
        settingLabel.setFont(new Font(settingLabel.getName(), Font.PLAIN, settingLabel.getFont().getSize()+2));
        setting.add(settingLabel);


        // ---------------------- Adding Mouse listener(Hover function) To Component of Left Panel -------------------------
        mouseHover(newMail, newMailLabel);
        mouseHover(inbox, inboxLabel);
        mouseHover(sent, sentLabel);
        mouseHover(setting, settingLabel);

        // ------------------------------------ Create JPanel for Send mail ----------------------------------
        JPanel newMailPanel = new JPanel(); // for new mail panel
        newMailPanel.setBounds(120, 50, 680, 450);
        newMailPanel.setBackground(new Color(234, 234, 234, 255));
        newMailPanel.setLayout(null);

        JLabel note = new JLabel("If You Want Send Mail to More Than One Person, You Should Write Comma(,) Between Username's.");
        note.setBounds(60, 20,600,20);
        newMailPanel.add(note);

        JTextField toInput = new JTextField();
        toInput.setBounds(120, 50,400,20);
        JLabel to = new JLabel("To");
        to.setBounds(60,50,40,20);
        to.setFont(new Font(to.getName(), Font.PLAIN, to.getFont().getSize()+3));
        newMailPanel.add(to);
        newMailPanel.add(toInput);

        JTextField subjectInput = new JTextField();
        subjectInput.setBounds(120, 80,400,20);
        JLabel subject = new JLabel("Subject");
        subject.setBounds(60,80,50,20);
        subject.setFont(new Font(subject.getName(), Font.PLAIN, subject.getFont().getSize()+3));
        newMailPanel.add(subject);
        newMailPanel.add(subjectInput);

        JTextArea textInput = new JTextArea();
        JLabel text = new JLabel("Text");
        text.setBounds(60,110,50,20);
        text.setFont(new Font(text.getName(), Font.PLAIN, text.getFont().getSize()+3));

        // scroll for JTextArea
        JScrollPane textScrollPane = new JScrollPane(textInput);
        textScrollPane.setBounds(120,110,400,220);
        newMailPanel.add(text);
        newMailPanel.add(textScrollPane);


        // Cancel and clear buttons
        JButton cancel = new JButton("Cancel");
        cancel.setBounds(120, 340, 140, 25);
        cancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancel.setFocusPainted(false);
        newMailPanel.add(cancel);
        JButton clear = new JButton("CLear Text");
        clear.setBounds(280, 340, 240, 25);
        clear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clear.setFocusPainted(false);
        newMailPanel.add(clear);


        JLabel send = new JLabel();
        ImageIcon sendIcon = new ImageIcon("img\\send.png");
        send.setBounds(555,50,50,50);
        send.setIcon(sendIcon);
        send.setCursor(new Cursor(Cursor.HAND_CURSOR));

        newMailPanel.add(send);

        clear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                textInput.setText("");
            }
        });
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newMailPanel.setVisible(false);
            }
        });
        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // you should fill all text fields for send Mail.
                if (!toInput.getText().isEmpty() && !subjectInput.getText().isEmpty() && !textInput.getText().isEmpty()) {
                    try {
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        // write ( "SendMail** sender username & receivers username & Mail's Subject  & Mail's Text" )

                        // if receivers username is correct, the mail will send.
                        out.writeUTF("SendMail**" + username + "&" + toInput.getText()
                        + "&" + subjectInput.getText() + "&" + textInput.getText());

                        // invalidUsername = "SomeUsernameNotFound** ,user1,user2 ....."
                        String invalidUsername = in.readUTF().substring(22);
                        if (invalidUsername.length()==0) { // if all username is valid
                            JOptionPane.showMessageDialog(null,
                                    "Send Mail is Successful",
                                    "Successfully",
                                    JOptionPane.PLAIN_MESSAGE);

                        } else {  // if length of invalid username bigger than 1. (that means the is at least one invalid username)

                            // if number of invalid username equals 1
                            if ( invalidUsername.split(",").length == 2 ) {
                                JOptionPane.showMessageDialog(null,
                                        "This Username: " + invalidUsername.split(",")[1] + " is Invalid.\n" + "Please Enter Valid Username",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            } else { // if number of invalid username more than 1
                                JOptionPane.showMessageDialog(null,
                                        "These Username's: " + invalidUsername.substring(1) + " are Invalid.\n" + "Please Enter Valid Username",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        subjectInput.setText("");
                        toInput.setText("");
                        textInput.setText("");

                    } catch (IOException ioException) {
                        ioException.getStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Please Fill All Text Fields",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }


            }
        });


        newMailPanel.setVisible(false);
        Email.add(newMailPanel);

        // ---------------------------------------  Create panel for Setting -----------------------------------
        JPanel settingPanel = new JPanel(); // for setting panel
        settingPanel.setBounds(120, 50, 680, 450);
        settingPanel.setLayout(null);
        settingPanel.setBackground(new Color(234, 234, 234, 255));

        JLabel userNameLabel = new JLabel("Username");
        userNameLabel.setBounds(110,50,70,22);
        settingPanel.add(userNameLabel);
        userNameLabel.setFont(new Font(userNameLabel.getName(), Font.BOLD, userNameLabel.getFont().getSize()+2));

        JTextField userNameField = new JTextField();
        userNameField.setBounds(190,50,300,22);
        settingPanel.add(userNameField);


        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(110,100,70,22);
        settingPanel.add(passwordLabel);
        passwordLabel.setFont(new Font(passwordLabel.getName(), Font.BOLD, passwordLabel.getFont().getSize()+2));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(190,100,300,22);
        settingPanel.add(passwordField);


        JLabel firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(110,150,70,22);
        settingPanel.add(firstNameLabel);
        firstNameLabel.setFont(new Font(firstNameLabel.getName(), Font.BOLD, firstNameLabel.getFont().getSize()+2));

        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(190,150,300,22);
        settingPanel.add(firstNameField);


        JLabel lastNamedLabel = new JLabel("Last Name");
        lastNamedLabel.setBounds(110,200,70,22);
        settingPanel.add(lastNamedLabel);
        lastNamedLabel.setFont(new Font(lastNamedLabel.getName(), Font.BOLD, lastNamedLabel.getFont().getSize()+2));

        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(190,200,300,22);
        settingPanel.add(lastNameField);


        // ---------------------------- trade data with server ------------------------
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            // write setting with username to server
            out.writeUTF("Setting**" + username);

            String line = in.readUTF();
            String[] data = line.split("&", 4); // give user data from server

            userNameField.setText(data[0]); // show username for user
            passwordField.setText(data[1]); // show password for user
            firstNameField.setText(data[2]); // show first name for user
            lastNameField.setText(data[3]); // show last name for user

        } catch (IOException ioException) {
            ioException.getStackTrace();
        }

        // cancel button
        JButton cancelChanges = new JButton("Cancel");
        cancelChanges.setBounds(190, 250, 90,25);
        cancelChanges.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingPanel.add(cancelChanges);

        // save changes button
        JButton saveChanges = new JButton("Save Changes");
        saveChanges.setBounds(290, 250, 200,25);
        saveChanges.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingPanel.add(saveChanges);

        // ------------------ add mouse listener for cancel and save buttons ------------------
        cancelChanges.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingPanel.setVisible(false);
            }
        });
        saveChanges.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    // send username to server
                    out.writeUTF("ChangeData**" + username);

                    // write new username to server for checking ...
                    out.writeUTF(userNameField.getText());

                    // if new username is available or new username equals old username!
                    if (in.readBoolean() || username.equals(userNameField.getText())) {
                        // change user's full name in top panel
                        setUsername(userNameField.getText()); // change username in email frame
                        hiUser.setText("Hi "+ firstNameField.getText() + " " + lastNameField.getText());

                        // write all new data to server for editing ...
                        out.writeUTF(userNameField.getText() + "&" +
                                new String(passwordField.getPassword()) + "&" +
                                firstNameField.getText() + "&" +
                                lastNameField.getText());

                        JOptionPane.showMessageDialog(null,
                                "The User's Data is Updated :)",
                                "Change Data",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                    else { // if your new username is not available!!!
                        JOptionPane.showMessageDialog(null,
                                "Sorry, This Username in not Available :(",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IOException ioException) {
                    ioException.getStackTrace();
                }
            }
        });

        Email.add(settingPanel);
        settingPanel.setVisible(false);

        // ---------------------------------- Create panel for show mail ----------------------------------------
        JPanel showMail = new JPanel(null);
        showMail.setBounds(120, 50, 680, 450);

        JLabel mailSubject = new JLabel(); // for mail's subject
        mailSubject.setBounds(70,20,200,40);
        mailSubject.setFont(new Font(mailSubject.getName(), Font.BOLD, 14));
        showMail.add(mailSubject);

        JLabel mailReceivers = new JLabel(); // for mail's receivers
        mailReceivers.setBounds(70,50,200,40);
        showMail.add(mailReceivers);

        JLabel mailSender = new JLabel(); // for mail's receivers
        mailSender.setBounds(70,50,200,40);
        showMail.add(mailSender);

        JLabel mailTime = new JLabel();
        mailTime.setBounds(460,20,150,40);
        showMail.add(mailTime);

        JTextArea mailText = new JTextArea();
        mailText.setBounds(70,90,500,300);
        showMail.add(mailText);

        showMail.setVisible(false);
        Email.add(showMail);

        // ---------------------------------- Create panel for inbox mail ---------------------------------------
        JPanel inboxPanel = new JPanel(); // for inbox mail panel
        inboxPanel.setBounds(120, 50, 680, 450);
        inboxPanel.setLayout(null);
        inboxPanel.setBackground(new Color(234, 234, 234, 255));

        Email.add(inboxPanel);
        inboxPanel.setVisible(false);
        inbox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inboxPanel.removeAll();
                showMail.setVisible(false);
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    out.writeUTF("InboxMail**"+username);

                    int num = in.readInt();
                    Mail inboxMail = null;
                    JPanel mailsPanel; // mail's panel list
                    for (int i=0; i<num; i++) {
                        inboxMail = new Mail(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF());

                        // ----------- give data from server and create list of sent mails ----------------
                        mailsPanel = new JPanel(null);
                        mailsPanel.setBounds(0, 41*i, 680, 40);

                        if (i%2==0) {
                            mailsPanel.setBackground(new Color(255, 255, 255));
                        } else {
                            mailsPanel.setBackground(new Color(238, 238, 238));
                        }

                        JLabel subject = new JLabel("Subject: " + inboxMail.getSubject());
                        JLabel sender = new JLabel("From: " + inboxMail.getSender());
                        JLabel time = new JLabel(inboxMail.getTime());
                        JTextArea textArea = new JTextArea(inboxMail.getText());

                        subject.setBounds(10,0,200,40);
                        sender.setBounds(220, 0, 280, 40);
                        time.setBounds(530,0,130,40);

                        mailsPanel.add(subject);
                        mailsPanel.add(sender);
                        mailsPanel.add(time);


                        inboxPanel.add(mailsPanel);
                        mailsPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        mailsPanel.addMouseListener(new MouseAdapter() {

                            // show mail's full data when click on mail.
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                mailSubject.setText(subject.getText());
                                mailReceivers.setText("");
                                mailSender.setText(sender.getText());
                                mailTime.setText(time.getText());
                                mailText.setText(textArea.getText());
                                showMail.setVisible(true);
                            }
                        });

                    }

                    newMailPanel.setVisible(false);
                    settingPanel.setVisible(false);
                    inboxPanel.setVisible(true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // ----------------------------  Create panel for sent mail -----------------------------------
        JPanel sentPanel = new JPanel(); // for sent mail panel
        sentPanel.setBounds(120, 50, 680, 450);
        sentPanel.setLayout(null);
        sentPanel.setBackground(new Color(234, 234, 234, 255));

        Email.add(sentPanel);
        sentPanel.setVisible(false);
        sent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                sentPanel.removeAll();
                showMail.setVisible(false);
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());


                    out.writeUTF("SentMail**"+username); // write username to server

                    int num = in.readInt();

                    Mail sentMail = null;
                    JPanel mailsPanel; // mail's panel list
                    for (int i=0; i<num; i++) {
                        sentMail = new Mail(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF());

                        // ----------- give data from server and create list of sent mails ----------------
                        mailsPanel = new JPanel(null);
                        mailsPanel.setBounds(0, 41*i, 680, 40);

                        if (i%2==0) {
                            mailsPanel.setBackground(new Color(255, 255, 255));
                        } else {
                            mailsPanel.setBackground(new Color(238, 238, 238));
                        }

                        JLabel subject = new JLabel("Subject: " + sentMail.getSubject());
                        JLabel receivers = new JLabel("To: " + sentMail.getReceivers());
                        JLabel time = new JLabel(sentMail.getTime());
                        JTextArea textArea = new JTextArea(sentMail.getText());

                        subject.setBounds(10,0,200,40);
                        receivers.setBounds(220, 0, 280, 40);
                        time.setBounds(530,0,130,40);

                        mailsPanel.add(subject);
                        mailsPanel.add(receivers);
                        mailsPanel.add(time);


                        sentPanel.add(mailsPanel);
                        mailsPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        mailsPanel.addMouseListener(new MouseAdapter() {

                            // show mail's full data when click on mail.
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                mailSubject.setText(subject.getText());
                                mailReceivers.setText(receivers.getText());
                                mailTime.setText(time.getText());
                                mailText.setText(textArea.getText());
                                showMail.setVisible(true);
                            }
                        });

                    }

                    newMailPanel.setVisible(false);
                    settingPanel.setVisible(false);
                    inboxPanel.setVisible(false);
                    sentPanel.setVisible(true);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        // ---------------------- Add window listener to Email Frame -------------------------
        Email.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                login.setVisible(true);
            }
        });

        // ---------------------- Add Mouse Listener to Left panels ---------------------------
        newMail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                newMailPanel.setVisible(true);
                inboxPanel.setVisible(false);
                sentPanel.setVisible(false);
                showMail.setVisible(false);
                settingPanel.setVisible(false);

            }
        });
        setting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                settingPanel.setVisible(true);
                newMailPanel.setVisible(false);
                inboxPanel.setVisible(false);
                showMail.setVisible(false);
                sentPanel.setVisible(false);
            }
        });


        Email.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Email.setResizable(false);
        Email.setVisible(true);
    }

    public void setUsername(String username) { // for set username in email frame
        this.username = username;
    }

    static public void mouseHover(JPanel p, JLabel l) {
        p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                p.setBackground(Color.WHITE);
                l.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                p.setBackground(new Color(0, 57, 94));
                l.setForeground(Color.WHITE);
            }
        });

    } // if hover on a button, it's background will change

}
