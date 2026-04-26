package com.inventory.airtel.ui;

import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

/**
 * @Component is commented out to allow the web application to run 
 * in a headless server environment like Railway.
 */
// @Component 
public class LoginFrame extends JFrame {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private MainFrame mainFrame;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public void init() {
        setTitle("Airtel Inventory - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(230, 0, 0));
        header.setPreferredSize(new Dimension(400, 100));
        JLabel lblTitle = new JLabel("AIRTEL LOGIN");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(lblTitle);

        JPanel form = new JPanel(null);
        form.setBackground(Color.WHITE);

        JLabel lblUser = new JLabel("Username / Email:");
        lblUser.setBounds(50, 50, 300, 25);
        txtUsername = new JTextField();
        txtUsername.setBounds(50, 80, 300, 35);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 130, 300, 25);
        txtPassword = new JPasswordField();
        txtPassword.setBounds(50, 160, 300, 35);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setBounds(50, 230, 300, 45);
        btnLogin.setBackground(new Color(230, 0, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 16));

        btnLogin.addActionListener(e -> handleLogin());

        form.add(lblUser); form.add(txtUsername);
        form.add(lblPass); form.add(txtPassword);
        form.add(btnLogin);

        add(header, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);

        // setVisible(true); // Disable this for headless environments
    }

    private void handleLogin() {
        String user = txtUsername.getText();
        String pass = new String(txtPassword.getPassword());

        boolean success = inventoryService.authenticate(user, pass);

        if (success) {
            this.dispose(); 
            mainFrame.init();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}