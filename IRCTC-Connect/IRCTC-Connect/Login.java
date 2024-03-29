package IRCTC-Connect;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import Classes.*;

public class Login extends JFrame implements ActionListener {
    private JLabel l1, l2, l3;
    private JTextField tf1;
    private JPasswordField pf2;
    private JButton b1, b2, b3;
    private Connect connection;

    Login(Connect connection) {
        this.connection = connection;

        setFont(new Font("System", Font.BOLD, 22));
        Font f = getFont();
        FontMetrics fm = getFontMetrics(f);
        int x = fm.stringWidth("LOGIN DIALOG BOX");
        int y = fm.stringWidth(" ");
        int z = getWidth() - x;
        int w = z / y;
        String pad = "";
        pad = String.format("%" + w * 1.4 + "s", pad);
        setTitle(pad + "LOGIN DIALOG BOX");

        l1 = new JLabel("LOGIN");
        l1.setFont(new Font("Times new roman", Font.BOLD, 38));

        l2 = new JLabel("USER ID:");
        l2.setFont(new Font("Times new roman", Font.BOLD, 28));

        l3 = new JLabel("PASSWORD:");
        l3.setFont(new Font("Times new roman", Font.BOLD, 28));

        tf1 = new JTextField(15);
        pf2 = new JPasswordField(15);

        b1 = new JButton("LOG IN");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);

        b2 = new JButton("CLEAR");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);

        b3 = new JButton("SIGN UP");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);

        setLayout(null);

        l1.setBounds(175, 10, 200, 32);
        add(l1);

        l2.setBounds(25, 75, 150, 32);
        add(l2);

        tf1.setBounds(200, 75, 230, 30);
        add(tf1);

        l3.setBounds(25, 150, 200, 32);
        add(l3);

        tf1.setFont(new Font("Times new roman", Font.BOLD, 14));
        pf2.setFont(new Font("Times new roman", Font.BOLD, 14));
        pf2.setBounds(200, 150, 230, 30);
        add(pf2);
        b1.setFont(new Font("Times new roman", Font.BOLD, 14));
        b1.setBounds(25, 225, 200, 30);
        add(b1);

        b2.setFont(new Font("Times new roman", Font.BOLD, 14));
        b2.setBounds(275, 225, 200, 30);
        add(b2);

        b3.setFont(new Font("Times new roman", Font.BOLD, 14));
        b3.setBounds(125, 300, 230, 30);
        add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        getContentPane().setBackground(Color.WHITE);

        setSize(500, 400);
        setLocation(500, 250);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent ae) {
        try {

            if (ae.getSource() == b1) {
                String username = tf1.getText();
                char[] password = pf2.getPassword();
                LoginInfo user = new LoginInfo(username, password);
                try {

                    ObjectOutputStream os = new ObjectOutputStream(connection.socket.getOutputStream());
                    os.writeInt(1);
                    os.writeObject(user);
                    os.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ObjectInputStream oi = new ObjectInputStream(connection.socket.getInputStream());
                Userid userid = (Userid) oi.readObject();

                if (userid.getUsername().equals("")) {

                    JOptionPane.showMessageDialog(null, "Username or Password entered is Incorrect");
                } else {

                    new HomePage(connection, userid.getUsername(), userid.getUserid()).setVisible(true);
                    setVisible(false);
                }

            } else if (ae.getSource() == b2) {
                tf1.setText("");
                pf2.setText("");
            } else if (ae.getSource() == b3) {
                new SignUp(connection).setVisible(true);
                setVisible(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        Connect connection = new Connect();
        new Login(connection).setVisible(true);

    }

}
