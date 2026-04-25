package com.inventory.airtel.ui;

import com.inventory.airtel.model.Contact;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.*;

@Component
public class ContactForm extends JFrame {

    @Autowired private InventoryService inventoryService;
    @Autowired @Lazy private MainFrame mainFrame;

    private Long currentContactId = null;
    private JTextField txtEmpId = new JTextField();
    private JTextField txtName = new JTextField();
    private JTextField txtDept = new JTextField();
    private JTextField txtPhone = new JTextField();
    private JButton btnSave = new JButton("Register Employee");

    public void prepareForUpdate(Contact contact) {
        
        init(); 
        this.currentContactId = contact.getId();
        txtEmpId.setText(contact.getEmployeeId());
        txtName.setText(contact.getName());
        txtDept.setText(contact.getDepartment());
        txtPhone.setText(contact.getPhone());
        
        btnSave.setText("Update Staff");
        btnSave.setBackground(new Color(40, 40, 40)); 
        setVisible(true);
    }

    public void init() {
        
        if (currentContactId == null) {
            clearFields();
            btnSave.setText("Register Employee");
            btnSave.setBackground(new Color(230, 0, 0));
        }

        setTitle("Airtel Staff Registration");
        setSize(400, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(new Color(230, 0, 0));
        JLabel lblHeader = new JLabel("STAFF MANAGEMENT");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 14));
        pnlHeader.add(lblHeader);

        
        JPanel pnlForm = new JPanel(new GridLayout(5, 2, 10, 15));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        pnlForm.add(new JLabel("Employee ID:")); pnlForm.add(txtEmpId);
        pnlForm.add(new JLabel("Full Name:"));    pnlForm.add(txtName);
        pnlForm.add(new JLabel("Department:"));  pnlForm.add(txtDept);
        pnlForm.add(new JLabel("Phone:"));       pnlForm.add(txtPhone);
        
        btnSave.setForeground(Color.WHITE);
        pnlForm.add(new JLabel("")); pnlForm.add(btnSave);

        
        getContentPane().removeAll();
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);
        revalidate();
        repaint();

        
        for(var al : btnSave.getActionListeners()) btnSave.removeActionListener(al);
        
        btnSave.addActionListener(e -> {
            Contact contact = new Contact();
            contact.setEmployeeId(txtEmpId.getText());
            contact.setName(txtName.getText());
            contact.setDepartment(txtDept.getText());
            contact.setPhone(txtPhone.getText());

            if (currentContactId == null) {
                inventoryService.saveContact(contact);
                JOptionPane.showMessageDialog(this, "New Staff Added!");
            } else {
                contact.setId(currentContactId);
                inventoryService.updateContact(contact); 
                JOptionPane.showMessageDialog(this, "Staff Record Updated!");
                currentContactId = null; 
            }

            mainFrame.refreshData();
            this.dispose();
        });
    }

    private void clearFields() {
        txtEmpId.setText("");
        txtName.setText("");
        txtDept.setText("");
        txtPhone.setText("");
    }
}