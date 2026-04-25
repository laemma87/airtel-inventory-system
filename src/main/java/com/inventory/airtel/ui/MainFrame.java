package com.inventory.airtel.ui;

import com.inventory.airtel.model.Asset;
import com.inventory.airtel.model.Contact;
import com.inventory.airtel.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Component
public class MainFrame extends JFrame {

    @Autowired private InventoryService inventoryService;
    @Autowired @Lazy private AssetForm assetForm;
    @Autowired @Lazy private ContactForm contactForm;
    @Autowired @Lazy private LoginFrame loginFrame;

    private DefaultTableModel tableModel;
    private JTable assetTable;
    private JScrollPane scrollPane;
    private JLabel lblAssetCount = new JLabel("0");
    private JLabel lblStaffCount = new JLabel("0");

    public void init() {
        setTitle("Airtel Inventory Management - LAEMMA INFO TECH");
        setSize(1300, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(230, 0, 0));
        header.setPreferredSize(new Dimension(1200, 70));
        JLabel title = new JLabel("  AIRTEL ASSET MANAGEMENT");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        header.add(title, BorderLayout.WEST);

        
        JPanel sideNav = new JPanel();
        sideNav.setLayout(new BoxLayout(sideNav, BoxLayout.Y_AXIS));
        sideNav.setBackground(new Color(40, 40, 40));
        sideNav.setPreferredSize(new Dimension(200, 0));
        
        String[] links = {"Dashboard", "Register Asset", "Issue/Return", "Staff Contacts", "Reports", "Logout"};
        for (String link : links) {
            JButton btn = new JButton(link);
            btn.setMaximumSize(new Dimension(200, 50));
            btn.setBackground(new Color(40, 40, 40));
            
            if(link.equals("Reports")) btn.setForeground(new Color(255, 200, 0));
            else if(link.equals("Logout")) btn.setForeground(new Color(255, 100, 100));
            else btn.setForeground(Color.WHITE);

            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            sideNav.add(btn);
            
            if(link.equals("Dashboard")) btn.addActionListener(e -> showAssetView());
            if(link.equals("Register Asset")) btn.addActionListener(e -> { assetForm.init(); assetForm.setVisible(true); });
            if(link.equals("Staff Contacts")) btn.addActionListener(e -> showStaffView());
            if(link.equals("Issue/Return")) btn.addActionListener(e -> handleIssueReturn());
            if(link.equals("Reports")) btn.addActionListener(e -> showReportsMenu(btn));
            if(link.equals("Logout")) btn.addActionListener(e -> handleLogout());
        }

        
        JPanel mainBody = new JPanel(new BorderLayout());
        JPanel statsGrid = new JPanel(new GridLayout(1, 3, 20, 0));
        statsGrid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        
        statsGrid.add(createStatCard("Total Assets", lblAssetCount, new Color(230, 0, 0), () -> showAssetView()));
        statsGrid.add(createStatCard("Staff Registered", lblStaffCount, new Color(50, 50, 50), () -> showStaffView()));
        statsGrid.add(createStatCard("System Status", new JLabel("ONLINE"), new Color(0, 150, 136), () -> refreshData()));

        tableModel = new DefaultTableModel();
        assetTable = new JTable(tableModel);
        assetTable.setRowHeight(40);
        scrollPane = new JScrollPane(assetTable);

        mainBody.add(statsGrid, BorderLayout.NORTH);
        mainBody.add(scrollPane, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
        add(sideNav, BorderLayout.WEST);
        add(mainBody, BorderLayout.CENTER);
        
        JLabel statusLabel = new JLabel(" @ Airtel 2026 All Rights Reserved | Rusizi Hub");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
        add(statusLabel, BorderLayout.SOUTH);

        showAssetView();
        setVisible(true);
    }

    

    private JPanel createStatCard(String title, JLabel valueLabel, Color color, Runnable action) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, color));
        card.addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) { action.run(); }
        });
        JLabel t = new JLabel(title);
        t.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        card.add(t, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this, "Confirm Logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
            loginFrame.init();
        }
    }

    private void showAssetView() {
        String[] cols = {"ID", "Tag ID", "Serial", "Type", "Model", "Condition", "Status", "User", "Actions"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 8; }
        };
        assetTable.setModel(tableModel);
        
        refreshData();
    }

    private void showStaffView() {
        String[] cols = {"ID", "Emp ID", "Name", "Department", "Phone", "Actions"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return c == 5; }
        };
        assetTable.setModel(tableModel);
        refreshData();
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Asset> assets = inventoryService.getAllAssets();
        List<Contact> contacts = inventoryService.getAllContacts();
        
        if (assetTable.getColumnCount() > 6) {
            for (Asset a : assets) {
                tableModel.addRow(new Object[]{a.getId(), a.getTagId(), a.getSerialNumber(), a.getDeviceType(), a.getModel(), a.getDeviceCondition(), a.getStatus(), a.getAssignedTo(), ""});
            }
        } else {
            for (Contact c : contacts) {
                tableModel.addRow(new Object[]{c.getId(), c.getEmployeeId(), c.getName(), c.getDepartment(), c.getPhone(), ""});
            }
        }
        lblAssetCount.setText(String.valueOf(assets.size()));
        lblStaffCount.setText(String.valueOf(contacts.size()));
    }

    private void showReportsMenu(JButton source) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Export Inventory (PDF)");
        item.addActionListener(e -> JOptionPane.showMessageDialog(this, "Generating Report for Rusizi Hub..."));
        menu.add(item);
        menu.show(source, source.getWidth(), 0);
    }

    private void handleIssueReturn() {
        int row = assetTable.getSelectedRow();
        if (row == -1 || assetTable.getColumnCount() < 7) {
            JOptionPane.showMessageDialog(this, "Please select an asset from the table.");
            return;
        }
        Long id = (Long) tableModel.getValueAt(row, 0);
        String status = (String) tableModel.getValueAt(row, 6);
        
        if ("In Store".equals(status)) {
            String user = JOptionPane.showInputDialog(this, "Assign to Staff Name:");
            if (user != null && !user.isEmpty()) inventoryService.updateAssetStatus(id, "Issued", user, "Good");
        } else {
            inventoryService.updateAssetStatus(id, "In Store", "N/A", "Checked");
        }
        refreshData();
    }
}