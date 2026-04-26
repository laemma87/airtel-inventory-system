package com.inventory.airtel.ui;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * COMMENT OUT @Component BELOW!
 * This prevents Railway from crashing because it can't open desktop windows.
 */
// @Component 
public class AssetForm extends JFrame {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    @Lazy
    private MainFrame mainFrame;

    private Long currentId = null;
    private JTextField txtTagId = new JTextField();
    private JTextField txtSerial = new JTextField();
    private JTextField txtModel = new JTextField();
    private JComboBox<String> cbType = new JComboBox<>(new String[]{"Laptop", "Desktop", "Mobile", "Printer", "Scanner"});
    private JComboBox<String> cbCondition = new JComboBox<>(new String[]{"New", "Good", "Damaged", "Under Repair"});
    private JButton btnSave = new JButton("Register Asset");
    private JLabel lblHeader = new JLabel("NEW DEVICE REGISTRATION");

    public void prepareForUpdate(Asset asset) {
        init(); 
        this.currentId = asset.getId(); 
        
        txtTagId.setText(asset.getTagId());
        txtSerial.setText(asset.getSerialNumber());
        cbType.setSelectedItem(asset.getDeviceType());
        txtModel.setText(asset.getModel());
        cbCondition.setSelectedItem(asset.getDeviceCondition());

        lblHeader.setText("UPDATE ASSET DETAILS");
        btnSave.setText("Confirm Changes");
        btnSave.setBackground(new Color(0, 102, 204)); 
        setTitle("Editing Asset: " + asset.getTagId());
    }

    public void init() {
        if (currentId == null) {
            clearFields();
            btnSave.setText("Register Asset");
            btnSave.setBackground(new Color(230, 0, 0));
            lblHeader.setText("NEW DEVICE REGISTRATION");
        }

        setTitle("Airtel Asset Management");
        setSize(450, 450);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(currentId == null ? new Color(230, 0, 0) : new Color(0, 102, 204));
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 16));
        pnlHeader.add(lblHeader);

        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 10, 15));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        pnlForm.add(new JLabel("Asset Tag ID:")); pnlForm.add(txtTagId);
        pnlForm.add(new JLabel("Serial Number:")); pnlForm.add(txtSerial);
        pnlForm.add(new JLabel("Device Type:")); pnlForm.add(cbType);
        pnlForm.add(new JLabel("Model:")); pnlForm.add(txtModel);
        pnlForm.add(new JLabel("Condition:")); pnlForm.add(cbCondition);
        
        btnSave.setForeground(Color.WHITE);
        pnlForm.add(new JLabel("")); pnlForm.add(btnSave);

        getContentPane().removeAll(); 
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);

        btnSave.addActionListener(e -> {
            Asset asset = new Asset();
            asset.setTagId(txtTagId.getText());
            asset.setSerialNumber(txtSerial.getText());
            asset.setDeviceType(cbType.getSelectedItem().toString());
            asset.setModel(txtModel.getText());
            asset.setDeviceCondition(cbCondition.getSelectedItem().toString());
            
            if (currentId == null) {
                asset.setStatus("In Store");
                asset.setAssignedTo("Unassigned");
                inventoryService.saveAsset(asset);
                JOptionPane.showMessageDialog(this, "New Asset Saved Successfully!");
            } else {
                asset.setId(currentId);
                inventoryService.updateAssetDetails(asset);
                JOptionPane.showMessageDialog(this, "Asset Updated Successfully!");
                currentId = null; 
            }

            if (mainFrame != null) mainFrame.refreshData();
            this.dispose();
        });
    }

    private void clearFields() {
        txtTagId.setText("");
        txtSerial.setText("");
        txtModel.setText("");
        cbType.setSelectedIndex(0);
        cbCondition.setSelectedIndex(0);
    }
}